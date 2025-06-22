package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.response.CartResponse;
import com.snapcart.order_service.dto.response.PrecalculateResponse;

public interface CartService {
    CartResponse findCartByBuyerId(String buyerId);
    CartResponse updateCart(String buyerId, String productId, int quantity);
    CartResponse addToCart(String buyerId, String productId, int quantity);
    PrecalculateResponse precalculateCart(String buyerId);
}
