package com.kumarudhay121.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumarudhay121.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
