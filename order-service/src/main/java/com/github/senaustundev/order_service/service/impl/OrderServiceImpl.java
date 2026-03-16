package com.github.senaustundev.order_service.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.senaustundev.order_service.client.InventoryClient;
import com.github.senaustundev.order_service.dto.OrderRequest;
import com.github.senaustundev.order_service.modul.Order;
import com.github.senaustundev.order_service.repository.OrderRepository;
import com.github.senaustundev.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    // creates and places an order "createOrder()"
    @Override
    public void placeOrder(OrderRequest orderRequest) {

        // check if the product is in stock
        boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (inStock) {
            // create order
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            order.setPrice(orderRequest.price());

            orderRepository.save(order);
            log.info("Order {} placed successfully", order.getOrderNumber());
        } else {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock");
        }

    }

}
