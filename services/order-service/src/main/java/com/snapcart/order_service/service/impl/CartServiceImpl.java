package com.snapcart.order_service.service.impl;

import com.snapcart.order_service.dto.mapper.CartMapper;
import com.snapcart.order_service.dto.response.CartResponse;
import com.snapcart.order_service.dto.response.PrecalculateResponse;
import com.snapcart.order_service.dto.response.ProductInfo;
import com.snapcart.order_service.dto.response.UserInfo;
import com.snapcart.order_service.entity.CartEntity;
import com.snapcart.order_service.entity.CartLine;
import com.snapcart.order_service.repository.CartRepository;
import com.snapcart.order_service.service.CartService;
import com.snapcart.order_service.service.ProductServiceClient;
import com.snapcart.order_service.service.UserServiceClient;
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
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    @Override
    public CartResponse findCartByBuyerId(String buyerId) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            UserInfo userInfo = userServiceClient.getUserInfo(buyerId).getData();
            if (userInfo == null) {
                throw new RuntimeException("Not found any cart with id" + buyerId);
            }
            cartEntity = new CartEntity();
            cartEntity.setBuyerId(buyerId);
            cartEntity.setCartLines(List.of());
            cartEntity = cartRepository.save(cartEntity);
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

    @Override
    public PrecalculateResponse precalculateCart(String buyerId) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        List<String> productIds = cartEntity.getCartLines()
                .stream()
                .map(CartLine::getProductId)
                .toList();
        List<ProductInfo> product = productServiceClient.getProductInfoList(productIds).getData();


    }
}
