package com.github.senaustundev.order_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "inventory")
@Data
public class InventoryConfig {
    private String url;
}
