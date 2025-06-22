package com.snapcart.order_service.service.impl;

import com.snapcart.order_service.repository.OrderRepository;
import com.snapcart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
}
