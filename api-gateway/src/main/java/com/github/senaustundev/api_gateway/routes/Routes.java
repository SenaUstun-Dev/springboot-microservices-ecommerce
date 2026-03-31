package com.github.senaustundev.api_gateway.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import java.net.URI;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;

@Configuration
public class Routes {

        @Bean
        public RouterFunction<ServerResponse> productServiceRoutes() {
                return route("product-service")
                                .route(RequestPredicates.path("/api/products/**"), http())
                                .filter(lb("product-service"))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "productServiceCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceRoutes() {
                return route("order-service")
                                .route(RequestPredicates.path("/api/orders/**"), http())
                                .filter(lb("order-service"))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "orderServiceCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceRoutes() {
                return route("inventory-service")
                                .route(RequestPredicates.path("/api/inventories/**"), http())
                                .filter(lb("inventory-service"))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "inventoryServiceCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        // ----------------SWAGGER------------------

        @Bean
        public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
                return route("product_service_swagger")
                                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"), http())
                                .filter(lb("product-service"))
                                .before(rewritePath("/aggregate/product-service/v3/api-docs", "/v3/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
                return route("order_service_swagger")
                                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"), http())
                                .filter(lb("order-service"))
                                .before(rewritePath("/aggregate/order-service/v3/api-docs", "/v3/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
                return route("inventory_service_swagger")
                                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"), http())
                                .filter(lb("inventory-service"))
                                .before(rewritePath("/aggregate/inventory-service/v3/api-docs", "/v3/api-docs"))
                                .build();
        }

}
