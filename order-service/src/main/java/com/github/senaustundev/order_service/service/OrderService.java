package com.github.senaustundev.order_service.service;

import com.github.senaustundev.order_service.dto.OrderRequest;

public interface OrderService {
    public void placeOrder(OrderRequest orderRequest);
}
