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

import com.github.senaustundev.order_service.repository.OrderRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
 
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {
 
	@LocalServerPort
	private Integer port;

	@Autowired
	private OrderRepository orderRepository;
 
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
					"skuCode": "iphone_15",
					"price": 45000,
					"quantity": 1
				}
				""";

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/orders")
				.then()
				.statusCode(201);

		assertThat(orderRepository.findAll().size(), is(1));
	}
 
}
