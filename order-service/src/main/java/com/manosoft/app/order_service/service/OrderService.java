package com.manosoft.app.order_service.service;

import org.springframework.stereotype.Service;

import com.manosoft.app.commons.model.Order;
import com.manosoft.app.order_service.repository.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void newOrderRequest(Order order){
        orderRepository.save(order);
    }

}