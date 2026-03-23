package com.github.senaustundev.api_gateway.routes;

import java.net.URI;

import com.github.senaustundev.api_gateway.config.ServiceUrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class Routes {

	private final ServiceUrlProperties serviceUrlProperties;

	@Bean
	public RouterFunction<ServerResponse> productServiceRoutes() {
		return route("product-service")
				.route(RequestPredicates.path("/api/products/**"), http())
				.before(uri(URI.create(serviceUrlProperties.getProduct().getUrl())))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> orderServiceRoutes() {
		return route("order-service")
				.route(RequestPredicates.path("/api/orders/**"), http())
				.before(uri(URI.create(serviceUrlProperties.getOrder().getUrl())))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> inventoryServiceRoutes() {
		return route("inventory-service")
				.route(RequestPredicates.path("/api/inventories/**"), http())
				.before(uri(URI.create(serviceUrlProperties.getInventory().getUrl())))
				.build();
	}
}
