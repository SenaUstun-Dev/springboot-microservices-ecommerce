package com.github.senaustundev.inventory_service.service;

public interface InventoryService {
    public boolean isInStock(String skuCode, Integer quantity);
}
