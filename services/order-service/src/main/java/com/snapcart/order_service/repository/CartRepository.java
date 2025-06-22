package com.snapcart.order_service.repository;

import com.snapcart.order_service.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<CartEntity, String> {
    CartEntity findByBuyerId(String buyerId);
}
