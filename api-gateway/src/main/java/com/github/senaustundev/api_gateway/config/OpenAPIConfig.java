package com.github.senaustundev.api_gateway.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI apiGatewayAPI() {
        return new OpenAPI()
                .info(new Info().title("API Gateway")
                        .description("This is the REST API for API Gateway")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the API Gateway Wiki Documentation")
                        .url("https://api-gateway-dummy-url.com/docs"));
    }
}
