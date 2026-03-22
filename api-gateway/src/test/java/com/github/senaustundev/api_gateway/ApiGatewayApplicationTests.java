package com.github.senaustundev.api_gateway;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"services.product.url=http://localhost:${wiremock.port}",
		"services.order.url=http://localhost:${wiremock.port}",
		"services.inventory.url=http://localhost:${wiremock.port}"
})
@EnableWireMock({
		@ConfigureWireMock(name = "services", portProperties = "wiremock.port")
})
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
