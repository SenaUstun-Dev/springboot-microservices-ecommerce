package com.github.senaustundev.order_service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.kafka.core.KafkaTemplate;
import com.github.senaustundev.order_service.event.OrderPlacedEvent;
import com.github.senaustundev.order_service.repository.OrderRepository;
import com.github.senaustundev.order_service.stubs.InventoryClientStub;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"inventory.url=http://localhost:${inventory-service.port}"
})
@EnableWireMock({
		@ConfigureWireMock(name = "inventory-service", portProperties = "inventory-service.port")
})
class OrderServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@Autowired
	private OrderRepository orderRepository;

	@MockitoBean
	private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		RestAssured.proxy = null;
	}

	@AfterEach
	void tearDown() {
		orderRepository.deleteAll();
	}

	@Test
	void shouldPlaceOrder() {
		String requestBody = """
				{
					"orderNumber": "ORD-123",
					"skuCode": "iphone_15",
					"price": 45000,
					"quantity": 1,
					"userDetails": {
						"email": "test@test.com",
						"firstName": "John",
						"lastName": "Doe"
					}
				}
				""";
		InventoryClientStub.stubInventoryCall("iphone_15", 1);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(201);

		assertThat(orderRepository.findAll().size(), is(1));
	}

	@Test
	void shouldFailToPlaceOrderWithInvalidPriceType() {
		String requestBody = """
				{
					"skuCode": "iphone_15",
					"price": "not_a_number",
					"quantity": 1,
					"userDetails": {
						"email": "test@test.com",
						"firstName": "John",
						"lastName": "Doe"
					}
				}
				""";

		// InventoryClientStub.stubInventoryCall("iphone_15", "not_a_number");

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(400); // Spring/Jackson will return 400 for type mismatch
	}

	@Test
	void shouldFailToPlaceOrderWithZeroQuantity() {
		// Verifies that the system now REJECTS 0 quantity orders due to @Positive
		// validation
		String requestBody = """
				{
					"skuCode": "iphone_15",
					"price": 45000,
					"quantity": 0,
					"userDetails": {
						"email": "test@test.com",
						"firstName": "John",
						"lastName": "Doe"
					}
				}
				""";

		InventoryClientStub.stubInventoryCall("iphone_15", 0);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(400);
	}

	@Test
	void shouldFailToPlaceOrderWithEmptySku() {
		// NOTE: This test will FAIL (return 201) until validation annotations are added
		// to OrderRequest.java
		String requestBody = """
				{
					"skuCode": "",
					"price": 10.00,
					"quantity": 1,
					"userDetails": {
						"email": "test@test.com",
						"firstName": "John",
						"lastName": "Doe"
					}
				}
				""";

		InventoryClientStub.stubInventoryCall("", 1);

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(400);
	}
}
