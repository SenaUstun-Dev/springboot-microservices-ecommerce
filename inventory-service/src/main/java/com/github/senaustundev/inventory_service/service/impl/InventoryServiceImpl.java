package com.github.senaustundev.inventory_service.service.impl;

import org.springframework.stereotype.Service;

import com.github.senaustundev.inventory_service.repository.InventoryRepository;
import com.github.senaustundev.inventory_service.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public boolean isInStock(String skuCode, Integer quantity) {
        // Find an inventory for a given skuCode where quantity >= 0
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }
}
