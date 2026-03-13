package com.github.senaustundev.order_service.dto;

import java.math.BigDecimal;

public record OrderRequest(String orderNumber, String skuCode, Integer quantity, BigDecimal price) {

}
