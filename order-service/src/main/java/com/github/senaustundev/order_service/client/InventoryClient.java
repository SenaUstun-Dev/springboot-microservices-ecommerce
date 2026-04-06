package com.github.senaustundev.order_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(value = "inventory-service", url = "${inventory.url:}")
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetMapping("/api/inventories")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);

    default boolean fallbackMethod(String code, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for skucode {}, failure reason: {}", code, throwable.getMessage());
        return false;
    }
}
