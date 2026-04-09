package com.github.senaustundev.order_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
        String orderNumber,
        @NotBlank String skuCode,
        @Positive Integer quantity,
        @Positive BigDecimal price,
        UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName) {}
}
