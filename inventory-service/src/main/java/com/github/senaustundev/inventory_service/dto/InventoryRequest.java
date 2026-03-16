package com.github.senaustundev.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Positive;

public record InventoryRequest(
        @NotBlank String skuCode,
        // @Positive
        Integer quantity) {

}
