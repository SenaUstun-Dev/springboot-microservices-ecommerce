package com.github.senaustundev.inventory_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.senaustundev.inventory_service.dto.InventoryRequest;
import com.github.senaustundev.inventory_service.service.InventoryService;

//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<Boolean> isInStock(
            // @Valid
            InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.isInStock(request.skuCode(), request.quantity()));
    }
}
