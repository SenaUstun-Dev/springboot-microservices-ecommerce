package com.github.senaustundev.api_gateway.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services")
@Getter
@Setter
public class ServiceUrlProperties {

    private final ServiceConfig product = new ServiceConfig();
    private final ServiceConfig order = new ServiceConfig();
    private final ServiceConfig inventory = new ServiceConfig();

    @Getter
    @Setter
    public static class ServiceConfig {
        private String url;
    }
}
