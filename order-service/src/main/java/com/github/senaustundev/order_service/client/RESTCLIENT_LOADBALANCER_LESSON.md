# 🔍 Lesson: The `@LoadBalanced RestClient.Builder` Bean Conflict

> **Context:** After refactoring the `order-service` from **Spring Cloud OpenFeign** to the modern
> **Spring HTTP Interface** (`@HttpExchange` + `RestClient`), two unexpected and seemingly unrelated
> symptoms appeared. This document explains what happened, how we found it, what we tried, and what
> finally fixed it.

---

## 1. The Refactoring — What Changed

Before this branch, the `order-service` used **Feign** to call the `inventory-service`:

```java
// BEFORE (Feign)
@FeignClient(value = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventories")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
```

```xml
<!-- pom.xml — BEFORE -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

After refactoring, Feign was removed and replaced with the Spring-native HTTP Interface:

```java
// AFTER (Spring HTTP Interface)
@HttpExchange("/api/inventories")
public interface InventoryClient {
    @GetExchange
    boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
}
```

A new config class was added to wire up the `RestClient` with Eureka load balancing:

```java
// INITIAL InventoryClientConfig.java — THIS IS WHERE THE PROBLEM STARTED
@Configuration
public class InventoryClientConfig {

    @Bean
    @LoadBalanced
    RestClient.Builder restClientBuilder() {   // ← global pollutant
        return RestClient.builder();
    }

    @Bean
    public InventoryClient inventoryClient(RestClient.Builder builder) {
        RestClient restClient = builder
                .baseUrl("http://inventory-service")
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(InventoryClient.class);
    }
}
```

This looks innocent — but it introduced a **global side-effect** into the Spring context.

---

## 2. How The Symptoms Appeared

### Symptom 1 — Swagger 401 Unauthorized

After the refactor, accessing the aggregated Swagger UI via the API Gateway worked fine for
`product-service` and `inventory-service`, but **`order-service` returned a 401**:

```
Fetch error
response status is 401 /aggregate/order-service/v3/api-docs
```

The URL `http://localhost:9000/swagger-ui/index.html?urls.primaryName=order-service` failed,
while `?urls.primaryName=product-service` worked perfectly.

Since only the `order-service` showed this error, and the only difference between the branches was
the removal of Feign, the refactoring was clearly involved.

### Symptom 2 — Eureka Registration Failure

The `order-service` logs showed repeated errors:

```
WARN  No instances available for localhost
WARN  DiscoveryClient_ORDER-SERVICE/.../order-service:8081 - registration failed
      Cannot execute request on any known server
```

Meanwhile, every other service (`product-service`, `inventory-service`) registered with Eureka
successfully with `registration status: 204`. The `order-service` was started last, so it was
**not** a timing/startup-order issue.

---

## 3. The Exact Root Cause

In Spring Boot 4.x / Spring Cloud 2025.x, the **Eureka Client itself uses `RestClient` internally**
(via `RestClientEurekaHttpClient`) to communicate with the Eureka server.

When we defined this bean:

```java
@Bean
@LoadBalanced
RestClient.Builder restClientBuilder() {
    return RestClient.builder();
}
```

Spring Cloud's auto-configuration found it and wrapped it with `LoadBalancerInterceptor`. Because
this was the **only** `RestClient.Builder` bean in the context, Spring injected it **everywhere**
a `RestClient.Builder` was needed — including into **Eureka's own internal client**.

```
Eureka's RestClientEurekaHttpClient
    → gets Spring-injected RestClient.Builder
    → this builder has @LoadBalanced interceptor attached
    → tries to register at http://localhost:8761/eureka/
    → LoadBalancer intercepts the call, treat "localhost" as a service name
    → looks up "localhost" in Eureka registry → not found
    → throws: "No instances available for localhost"
    → Eureka registration FAILS
```

The same interceptor was also breaking `springdoc-openapi`'s internal requests when trying to
generate/fetch API docs — causing the 401 (actually a routing/resolution failure surfaced
through the gateway security layer) for `order-service` in Swagger UI.

Think of it as: **you put a GPS-only toll booth on every road in the city, including internal
roads that don't need GPS at all.**

---

## 4. Solutions We Tried (and Why They Didn't Work)

### ❌ Attempt 1 — Rename the bean + explicit qualifier

```java
// Renamed the bean and added explicit @LoadBalanced qualifier at injection
@Bean
@LoadBalanced
RestClient.Builder loadBalancedRestClientBuilder() {  // ← renamed
    return RestClient.builder();
}

@Bean
public InventoryClient inventoryClient(@LoadBalanced RestClient.Builder builder) {  // ← qualified
    ...
}
```

**Why it didn't work:** Renaming the bean doesn't change the fact that it is **still the only
`RestClient.Builder` in the context**. When Eureka's internal `RestClientEurekaHttpClient` asks
Spring for a `RestClient.Builder`, it doesn't use `@LoadBalanced` — it expects the plain default.
Since only one builder exists (ours, which is load-balanced), Eureka still picks it up.

---

### ❌ Attempt 2 — Change Eureka URL from `localhost` to `127.0.0.1`

```properties
# Changed from:
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
# To:
eureka.client.service-url.defaultZone=http://127.0.0.1:8761/eureka/
```

**Why it didn't work:** The `@LoadBalanced` interceptor attaches at **bean creation time**, not
at URL resolution time. It doesn't care what URL you give it at runtime. The interceptor still
intercepts the call and tries to load-balance `127.0.0.1` as a service name — which of course
doesn't exist in Eureka either. Error becomes: `No instances available for 127.0.0.1`.

---

## 5. The Fix That Worked — `@Primary` Plain Builder

The solution: introduce a **second `RestClient.Builder` bean** that is plain (no `@LoadBalanced`)
and mark it as `@Primary`. This makes it the **default** bean that Spring injects everywhere,
leaving the `@LoadBalanced` one available only when **explicitly requested with the qualifier**.

```java
@Configuration
public class InventoryClientConfig {

    // Special drawer — only for load-balanced calls to inventory-service
    @Bean
    @LoadBalanced
    RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    // Default drawer — used by Eureka, SpringDoc, and everything else
    @Bean
    @Primary
    RestClient.Builder defaultRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public InventoryClient inventoryClient(@LoadBalanced RestClient.Builder builder) {
        // Explicitly picks the @LoadBalanced builder via the qualifier
        RestClient restClient = builder
                .baseUrl("http://inventory-service")
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(InventoryClient.class);
    }
}
```

### Why This Works

| Component | Gets Which Builder | Result |
|---|---|---|
| `InventoryClient` | `@LoadBalanced` (explicit qualifier) | ✅ Resolves `inventory-service` via Eureka |
| `RestClientEurekaHttpClient` | `@Primary` plain (default) | ✅ Connects directly to `localhost:8761` |
| SpringDoc internals | `@Primary` plain (default) | ✅ Fetches `/v3/api-docs` cleanly |
| Any other component | `@Primary` plain (default) | ✅ Works as expected |

The `@Primary` annotation tells Spring: *"If someone asks for a `RestClient.Builder` without
specifying which one, give them this plain one."* The `@LoadBalanced` one only gets injected
when someone explicitly asks for it with the `@LoadBalanced` qualifier.

---

## 6. Key Lesson

> **⚠️ When you add a `@LoadBalanced` bean to a Spring context, you are not just configuring one
> client. You are potentially hijacking the default injection candidate for that type across the
> entire application — including internal Spring Cloud and SpringDoc components.**

### Rules to live by when using `@LoadBalanced RestClient.Builder`:

1. **Always also define a `@Primary` plain builder** when you add a `@LoadBalanced` one.
2. **Always use `@LoadBalanced` at the injection site** (not just on the bean declaration) to be
   explicit about which builder you want.
3. **Be aware that Spring Cloud internals (Eureka, SpringDoc, OAuth2) may use the same types.**
   If there's only one bean of that type, they'll get it — even if it's load-balanced.

### Why Feign didn't have this problem

Feign's `@FeignClient` is self-contained. It manages its own HTTP client pool internally and
never exposes a shared `RestClient.Builder` bean to the global Spring context. When you switch
to the HTTP Interface + `RestClient`, **you become responsible for managing the builder's scope
correctly** — Feign did it for you automatically.

---

*Documented from real-world debugging during the Feign → Spring HTTP Interface refactoring on
the `order-service`. Spring Boot 4.0.5 / Spring Cloud 2025.1.1.*
