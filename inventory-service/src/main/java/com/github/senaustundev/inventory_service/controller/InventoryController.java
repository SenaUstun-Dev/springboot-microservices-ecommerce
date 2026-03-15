package com.github.senaustundev.inventory_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.senaustundev.inventory_service.service.InventoryService;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<Boolean> isInStock(
            @RequestParam String skuCode,
            @RequestParam @Positive Integer quantity) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode, quantity));
    }
}
