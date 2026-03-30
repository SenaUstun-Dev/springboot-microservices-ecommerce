package com.github.senaustundev.order_service.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

//refactored from feign client to spring http interface
//this is a new feature in spring boot 3.2
//This refactor will not change the business logic of the order-service, only the mechanism used to communicate with the inventory-service

@HttpExchange("/api/inventories")
public interface InventoryClient {
    @GetExchange
    boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
}
