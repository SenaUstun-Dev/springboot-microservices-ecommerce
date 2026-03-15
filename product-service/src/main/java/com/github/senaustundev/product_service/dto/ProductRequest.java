package com.github.senaustundev.product_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductRequest(@NotBlank String name, String description, @Positive BigDecimal price) {

}
