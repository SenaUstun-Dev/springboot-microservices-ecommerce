package com.github.senaustundev.inventory_service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import com.github.senaustundev.inventory_service.modul.Inventory;
import com.github.senaustundev.inventory_service.repository.InventoryRepository;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@Autowired
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		RestAssured.proxy = null;
	}

	@AfterEach
	void tearDown() {
		inventoryRepository.deleteAll();
	}

	@Test
	void shouldReturnTrueWhenInventoryInStock() {
		// Seed data
		Inventory inventory = new Inventory();
		inventory.setSkuCode("iphone_15");
		inventory.setQuantity(100);
		inventoryRepository.save(inventory);

		boolean response = RestAssured.given()
				.when()
				.get("/api/inventories?skuCode=iphone_15&quantity=50")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.as(Boolean.class);

		org.hamcrest.MatcherAssert.assertThat(response, Matchers.is(true));
	}

	@Test
	void shouldReturnFalseWhenInventoryNotInStock() {
		// Seed data
		Inventory inventory = new Inventory();
		inventory.setSkuCode("iphone_15");
		inventory.setQuantity(100);
		inventoryRepository.save(inventory);

		boolean response = RestAssured.given()
				.when()
				.get("/api/inventories?skuCode=iphone_15&quantity=101")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.as(Boolean.class);

		org.hamcrest.MatcherAssert.assertThat(response, Matchers.is(false));
	}

	@Test
	void shouldReturnFalseWhenProductDoesNotExist() {
		boolean response = RestAssured.given()
				.when()
				.get("/api/inventories?skuCode=non_existent&quantity=1")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.as(Boolean.class);

		org.hamcrest.MatcherAssert.assertThat(response, Matchers.is(false));
	}

	// @Test
	// void shouldReturnBadRequestWhenQuantityIsNegative() {
	// RestAssured.given()
	// .when()
	// .get("/api/inventories?skuCode=iphone_15&quantity=-1")
	// .then()
	// .log().all()
	// .statusCode(500);
	// }

}
