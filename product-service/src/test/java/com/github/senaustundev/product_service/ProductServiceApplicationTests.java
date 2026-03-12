package com.github.senaustundev.product_service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		RestAssured.proxy = null; // Explicitly set to null to avoid Groovy-related NPE in some environments
	}

	@AfterEach
	void tearDown() {
		mongoTemplate.getDb().drop();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
					"name": "wrist band",
					"description": "put on wrist.",
					"price": 56.99
				}
				""";

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/products")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("wrist band"))
				.body("description", Matchers.equalTo("put on wrist."))
				.body("price", Matchers.equalTo(56.99f));
	}

	@Test
	void shouldGetAllProducts() {
		// Arrange — 2 ürün kaydet
		String product1 = """
				{
					"name": "wrist band",
					"description": "put on wrist.",
					"price": 56.99
				}
				""";
		String product2 = """
				{
					"name": "smart watch",
					"description": "track your health.",
					"price": 199.99
				}
				""";

		RestAssured.given().contentType(ContentType.JSON).body(product1).post("/api/products");
		RestAssured.given().contentType(ContentType.JSON).body(product2).post("/api/products");

		// Act & Assert
		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/api/products")
				.then()
				.statusCode(200)
				.body("size()", Matchers.equalTo(2))
				.body("[0].name", Matchers.equalTo("wrist band"))
				.body("[0].price", Matchers.equalTo(56.99f))
				.body("[1].name", Matchers.equalTo("smart watch"))
				.body("[1].price", Matchers.equalTo(199.99f));
	}

	@Test
	void shouldReturnEmptyListWhenNoProducts() {
		RestAssured.given()
				.contentType(ContentType.JSON)
				.when()
				.get("/api/products")
				.then()
				.statusCode(200)
				.body("size()", Matchers.equalTo(0));
	}
}
