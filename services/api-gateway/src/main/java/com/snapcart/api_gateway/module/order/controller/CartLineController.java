package com.snapcart.api_gateway.module.order.controller;

import com.snapcart.api_gateway.module.order.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartLineController {
    private final OrderServiceClient orderServiceClient;
    @GetMapping
    public Object getOrderLines(@RequestParam Map<String, String> params) {
        return orderServiceClient.filterOrder(params);
    }

    @PutMapping("{id}")
    public Object updateOrderLineStatus(@PathVariable("id") String id, @RequestBody Object request) {
        return orderServiceClient.updateOrderLineStatus(id, request);
    }
}
