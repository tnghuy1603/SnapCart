package com.snapcart.order_service.controller;

import com.snapcart.order_service.dto.request.AddToCartRequest;
import com.snapcart.order_service.dto.request.UpdateCartRequest;
import com.snapcart.order_service.dto.response.SnapCartResponse;
import com.snapcart.order_service.dto.response.CartLine;
import com.snapcart.order_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("{buyerId}")
    public ResponseEntity<?> getCart(@PathVariable("buyerId") String buyerId) {
        return SnapCartResponse.successResponse(cartService.findCartByBuyerId(buyerId), null);
    }

    @PostMapping("{buyerId}")
    public ResponseEntity<?> addToCart(@PathVariable("buyerId") String buyerId,
                                       @RequestBody AddToCartRequest request) {
        cartService.addToCart(buyerId, request.getProductId(),  request.getQuantity());
        return SnapCartResponse.successResponse("Add to cart successfully");
    }

    @PutMapping("{buyerId}")
    public ResponseEntity<?> updateCart(@PathVariable("buyerId") String buyerId,
                                        @RequestBody UpdateCartRequest request) {
        return SnapCartResponse.successResponse(cartService.updateCart(buyerId, request.getProductId(), request.getQuantity()), null);
    }

    @PostMapping("{buyerId}/pre-calculate")
    public ResponseEntity<?> precalculate(@PathVariable("buyerId") String buyerId, @RequestBody List<CartLine> cartLines) {
        return SnapCartResponse.successResponse(cartService.precalculateCart(buyerId, cartLines), null);
    }

    @GetMapping("{buyerId}/count-item")
    public ResponseEntity<?> getNumberOfItems(@PathVariable("buyerId") String buyerId) {
        return SnapCartResponse.successResponse(cartService.countTotalItemsInCart(buyerId), null);
    }


}
