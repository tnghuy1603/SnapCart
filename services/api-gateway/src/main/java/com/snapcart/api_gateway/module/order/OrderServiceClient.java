package com.snapcart.api_gateway.module.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "order-service", url = "${api.order-service-host}")
public interface OrderServiceClient {
    @PostMapping("orders/checkout")
    ResponseEntity<?> checkout(@RequestBody Object request);

    @GetMapping("orders")
    ResponseEntity<?> filterOrder(@RequestParam Map<String, String> params);

    @GetMapping("carts/{buyerId}")
    public ResponseEntity<?> getCart(@PathVariable("buyerId") String buyerId);

    @PostMapping("carts/{buyerId}")
    ResponseEntity<?> addToCart(@PathVariable("buyerId") String buyerId, @RequestBody Object request);
    @PutMapping("carts/{buyerId}")
    ResponseEntity<?> updateCart(@PathVariable("buyerId") String buyerId, @RequestBody Object request);

    @PostMapping("carts/{buyerId}/pre-calculate")
    ResponseEntity<?> precalculate(@PathVariable("buyerId") String buyerId, @RequestBody Object request);

    @GetMapping("carts/{buyerId}/count-item")
    ResponseEntity<?> getNumberOfItems(@PathVariable("buyerId") String buyerId);

    @GetMapping
    ResponseEntity<?> getOrderLines(@RequestParam Map<String, String> params);

    @PutMapping("{id}")
    ResponseEntity<?> updateOrderLineStatus(@PathVariable("id") String id, @RequestBody Object request);
}
