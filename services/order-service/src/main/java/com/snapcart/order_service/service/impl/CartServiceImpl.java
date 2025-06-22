package com.snapcart.order_service.service.impl;

import com.snapcart.order_service.dto.mapper.CartMapper;
import com.snapcart.order_service.dto.response.CartResponse;
import com.snapcart.order_service.entity.CartEntity;
import com.snapcart.order_service.entity.CartLine;
import com.snapcart.order_service.repository.CartRepository;
import com.snapcart.order_service.service.CartService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse findCartByBuyerId(String buyerId) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        return cartMapper.toModel(cartEntity);
    }

    @Override
    public CartResponse updateCart(String buyerId, String productId, int quantity) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        Map<String, CartLine> cartLineMap = cartEntity.getCartLines()
                .stream()
                .collect(Collectors.toMap(CartLine::getProductId, Function.identity()));
        if (cartLineMap.containsKey(productId)) {
            CartLine cartLine = cartLineMap.get(productId);
            cartLine.setQuantity(quantity);
        } else {
            CartLine cartLine = new CartLine();
            cartLine.setProductId(productId);
            cartLine.setQuantity(quantity);
            cartEntity.getCartLines().add(cartLine);
        }
        cartEntity = cartRepository.save(cartEntity);
        return cartMapper.toModel(cartEntity);
    }

    @Override
    public CartResponse addToCart(String buyerId, String productId, int quantity) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        Map<String, CartLine> cartLineMap = cartEntity.getCartLines()
                .stream()
                .collect(Collectors.toMap(CartLine::getProductId, Function.identity()));
        if (cartLineMap.containsKey(productId)) {
            CartLine cartLine = cartLineMap.get(productId);
            cartLine.setQuantity(cartLine.getQuantity() + quantity);
        } else {
            CartLine cartLine = new CartLine();
            cartLine.setProductId(productId);
            cartLine.setQuantity(quantity);
            cartEntity.getCartLines().add(cartLine);
        }
        cartEntity = cartRepository.save(cartEntity);
        return cartMapper.toModel(cartEntity);
    }
}
