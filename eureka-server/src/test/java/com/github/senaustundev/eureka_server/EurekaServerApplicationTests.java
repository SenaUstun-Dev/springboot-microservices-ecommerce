package com.github.senaustundev.eureka_server;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EurekaServerApplicationTests {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.proxy = null;
    }

    @Test
    void contextLoads() {
        // Basic sanity check to ensure context starts
    }

    @Test
    void shouldServeDashboard() {
        RestAssured.given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body(Matchers.containsString("System Status"))
                .body(Matchers.containsString("Eureka"));
    }

    @Test
    void shouldReturnEmptyRegistry() {
        RestAssured.given()
                .when()
                .get("/eureka/apps")
                .then()
                .statusCode(200)
                .body(Matchers.containsString("applications"));
    }
}
