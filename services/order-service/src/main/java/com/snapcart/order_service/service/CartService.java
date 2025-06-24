package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.response.CartResponse;
import com.snapcart.order_service.dto.response.PrecalculateResponse;
import com.snapcart.order_service.dto.response.CartLine;

import java.util.List;

public interface CartService {
    CartResponse findCartByBuyerId(String buyerId);
    CartResponse updateCart(String buyerId, String productId, int quantity);
    void addToCart(String buyerId, String productId, int quantity);
    int countTotalItemsInCart(String buyerId);
    PrecalculateResponse precalculateCart(String buyerId, List<CartLine> cartLines);
}
