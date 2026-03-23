package com.github.senaustundev.api_gateway.routes;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class Routes {

        @Bean
        public RouterFunction<ServerResponse> productServiceRoutes() {
                return route("product-service")
                                .route(RequestPredicates.path("/api/products/**"), http())
                                .before(uri(URI.create("lb://product-service")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceRoutes() {
                return route("order-service")
                                .route(RequestPredicates.path("/api/orders/**"), http())
                                .before(uri(URI.create("lb://order-service")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceRoutes() {
                return route("inventory-service")
                                .route(RequestPredicates.path("/api/inventories/**"), http())
                                .before(uri(URI.create("lb://inventory-service")))
                                .build();
        }
}
