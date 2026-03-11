package com.github.senaustundev.product_service.dto;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, BigDecimal price) {

}
