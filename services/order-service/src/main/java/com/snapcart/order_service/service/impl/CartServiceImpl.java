package com.snapcart.order_service.service.impl;

import com.snapcart.order_service.dto.response.*;
import com.snapcart.order_service.entity.CartEntity;
import com.snapcart.order_service.dto.response.CartLine;
import com.snapcart.order_service.exception.OrderServiceException;
import com.snapcart.order_service.repository.CartRepository;
import com.snapcart.order_service.service.CartService;
import com.snapcart.order_service.service.ProductServiceClient;
import com.snapcart.order_service.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    @Override
    public CartResponse findCartByBuyerId(String buyerId) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            SnapCartResponse<UserInfo> response = userServiceClient.getUserInfo(buyerId);
            UserInfo userInfo = response.getData();
            if (userInfo == null) {
                throw new RuntimeException("Not found any cart with id" + buyerId);
            }
            cartEntity = new CartEntity();
            cartEntity.setBuyerId(buyerId);
            cartEntity.setCartLines(Set.of());
            cartEntity = cartRepository.save(cartEntity);
        }
        return this.bindProductInfo(cartEntity);
    }

    @Override
    public CartResponse updateCart(String buyerId, String productId, int quantity) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw OrderServiceException.buildBadRequest("Cart not found with buyerId: " + buyerId);
        }

        Map<String, CartLine> cartLineMap = cartEntity.getCartLines()
                .stream()
                .collect(Collectors.toMap(CartLine::getProductId, Function.identity()));

        if (cartLineMap.containsKey(productId)) {
            CartLine cartLine = cartLineMap.get(productId);
            if (quantity == 0) {
                cartEntity.getCartLines().remove(cartLine);
            }
            cartLine.setQuantity(quantity);
        } else {
            CartLine cartLine = new CartLine();
            cartLine.setProductId(productId);
            cartLine.setQuantity(quantity);
            cartEntity.getCartLines().add(cartLine);
            cartEntity = cartRepository.save(cartEntity);
        }
        cartEntity = cartRepository.save(cartEntity);
        return this.bindProductInfo(cartEntity);
    }

    @Override
    public void addToCart(String buyerId, String productId, int quantity) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        ProductInfo productInfo = productServiceClient.getProductInfo(productId).getData();
        if (productInfo == null) {
            throw new RuntimeException("Product not found with productId: " + productId);
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
        cartRepository.save(cartEntity);
    }

    @Override
    public int countTotalItemsInCart(String buyerId) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        Set<CartLine> cartLineList = cartEntity.getCartLines();
        int numberOfItems = 0;
        for (CartLine cartLine : cartLineList) {
            numberOfItems = numberOfItems + cartLine.getQuantity();
        }
        return numberOfItems;
    }

    @Override
    public PrecalculateResponse precalculateCart(String buyerId, List<CartLine> itemToCheckout) {
        CartEntity cartEntity = cartRepository.findByBuyerId(buyerId);
        if (cartEntity == null) {
            throw new RuntimeException("Cart not found with buyerId: " + buyerId);
        }
        if (!new HashSet<>(cartEntity.getCartLines()).containsAll(itemToCheckout)) {
            throw new RuntimeException("One of the item is not in the cart");
        }

        BigDecimal shippingFee = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO.add(shippingFee);
        Map<String, ProductInfo> productInfoMap = getProductInfoMap(cartEntity.getCartLines());
        for (CartLine cartLine : itemToCheckout) {
            ProductInfo productInfo = productInfoMap.get(cartLine.getProductId());
            totalAmount = totalAmount.add(BigDecimal.valueOf(cartLine.getQuantity()).multiply(productInfo.getPrice()));
        }
        return PrecalculateResponse.builder()
                .subTotal(totalAmount)
                .shippingFee(shippingFee)
                .build();
    }

    private Map<String, ProductInfo> getCartItemProductInfo(CartEntity cartEntity) {
        List<String> productIds = cartEntity.getCartLines()
                .stream()
                .map(CartLine::getProductId)
                .toList();
        List<ProductInfo> productInfoList = productServiceClient.getProductInfoList(productIds).getData();
        return productInfoList.stream()
                .collect(Collectors.toMap(ProductInfo::getId, Function.identity()));
    }

    private Map<String, ProductInfo> getProductInfoMap(Set<CartLine> cartLines) {
        List<String> productIds = cartLines
                .stream()
                .map(CartLine::getProductId)
                .toList();
        List<ProductInfo> productInfoList = productServiceClient.getProductInfoList(productIds).getData();
        return productInfoList.stream()
                .collect(Collectors.toMap(ProductInfo::getId, Function.identity()));
    }

    private CartResponse bindProductInfo(CartEntity cartEntity) {
        Map<String, ProductInfo> productInfoMap = getCartItemProductInfo(cartEntity);
        List<CartLineResponse> cartLineResponses = new ArrayList<>();

        for (CartLine cartLine : cartEntity.getCartLines()) {
            ProductInfo productInfo = productInfoMap.get(cartLine.getProductId());
            BigDecimal subtotal = productInfo.getPrice().multiply(BigDecimal.valueOf(cartLine.getQuantity()));
            CartLineResponse cartLineResponse = CartLineResponse.builder()
                    .productId(cartLine.getProductId())
                    .quantity(cartLine.getQuantity())
                    .outOfStock(cartLine.getQuantity() > productInfo.getStock())
                    .price(productInfo.getPrice())
                    .name(productInfo.getName())
                    .subtotal(subtotal)
                    .images(productInfo.getImages())
                    .build();

            cartLineResponses.add(cartLineResponse);
        }
        return CartResponse.builder()
                .cartLines(cartLineResponses)
                .id(cartEntity.getId())
                .buyerId(cartEntity.getBuyerId())
                .createdAt(cartEntity.getCreatedAt())
                .updatedAt(cartEntity.getUpdatedAt())
                .build();
    }

}
