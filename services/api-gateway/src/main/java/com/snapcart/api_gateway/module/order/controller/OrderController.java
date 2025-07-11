package com.snapcart.api_gateway.module.order.controller;

import com.snapcart.api_gateway.module.order.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceClient orderServiceClient;
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Object request) {
        return orderServiceClient.checkout(request);
    }

    @GetMapping
    public ResponseEntity<?> filterOrder(@RequestParam Map<String, String> params) {
        return orderServiceClient.filterOrder(params);
    }
}
