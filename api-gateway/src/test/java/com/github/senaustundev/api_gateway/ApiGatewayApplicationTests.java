package com.github.senaustundev.api_gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:${wiremock.port}",
		"spring.cloud.discovery.client.simple.instances.product-service[0].uri=http://localhost:${wiremock.port}",
		"spring.cloud.discovery.client.simple.instances.order-service[0].uri=http://localhost:${wiremock.port}",
		"spring.cloud.discovery.client.simple.instances.inventory-service[0].uri=http://localhost:${wiremock.port}"
})
@EnableWireMock({
		@ConfigureWireMock(name = "services", portProperties = "wiremock.port")
})
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class ApiGatewayApplicationTests {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setup() {
		RestAssured.port = port;
	}

	@Test
	void shouldRouteToProductService() {
		WireMock.stubFor(get(urlEqualTo("/api/products"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"message\": \"Product Service Response\"}")));

		RestAssured.given()
				.when()
				.get("/api/products")
				.then()
				.statusCode(200);
	}

	@Test
	void shouldRouteToOrderService() {
		WireMock.stubFor(get(urlEqualTo("/api/orders"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"message\": \"Order Service Response\"}")));

		RestAssured.given()
				.when()
				.get("/api/orders")
				.then()
				.statusCode(200);
	}

	@Test
	void shouldRouteToInventoryService() {
		WireMock.stubFor(get(urlEqualTo("/api/inventories"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"message\": \"Inventory Service Response\"}")));

		RestAssured.given()
				.when()
				.get("/api/inventories")
				.then()
				.statusCode(200);
	}

}
