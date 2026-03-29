package com.github.senaustundev.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.senaustundev.inventory_service.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Find an inventory for a given skuCode where quantity >= 0
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
