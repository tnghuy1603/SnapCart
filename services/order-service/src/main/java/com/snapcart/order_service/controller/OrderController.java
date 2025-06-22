package com.snapcart.order_service.controller;

import com.snapcart.order_service.dto.request.CheckOutRequest;
import com.snapcart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckOutRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getOrders() {}
}
