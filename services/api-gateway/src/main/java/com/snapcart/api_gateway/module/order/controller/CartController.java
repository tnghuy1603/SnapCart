package com.snapcart.api_gateway.module.order.controller;

import com.snapcart.api_gateway.module.order.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartController {
    private final OrderServiceClient orderServiceClient;
    @GetMapping("{buyerId}")
    public Object getCart(@PathVariable("buyerId") String buyerId) {
        return orderServiceClient.getCart(buyerId);
    }

    @PostMapping("{buyerId}")
    public Object addToCart(@PathVariable("buyerId") String buyerId,
                                       @RequestBody Object request) {
        return orderServiceClient.addToCart(buyerId, request);
    }

    @PutMapping("{buyerId}")
    public Object updateCart(@PathVariable("buyerId") String buyerId,
                                        @RequestBody Object request) {
        return orderServiceClient.updateCart(buyerId, request);
    }

    @PostMapping("{buyerId}/pre-calculate")
    public Object precalculate(@PathVariable("buyerId") String buyerId, @RequestBody Object request) {
        return orderServiceClient.precalculate(buyerId, request);
    }

    @GetMapping("{buyerId}/count-item")
    public Object getNumberOfItems(@PathVariable("buyerId") String buyerId) {
        return orderServiceClient.getNumberOfItems(buyerId);
    }
}
