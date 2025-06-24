package com.snapcart.order_service.controller;

import com.snapcart.order_service.dto.request.CheckOutRequest;
import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.dto.response.SnapCartResponse;
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
        return SnapCartResponse.successResponse(orderService.createOrder(request), null);
    }

    @GetMapping
    public ResponseEntity<?> filterOrder(FilterOrderRequest request) {
        return SnapCartResponse.successResponse(orderService.filterOrder(request), null);
    }

}
