package com.github.senaustundev.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.senaustundev.order_service.modul.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
