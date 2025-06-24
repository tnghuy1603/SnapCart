package com.snapcart.order_service.repository;

import com.snapcart.order_service.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, String>, OrderRepositoryCustom {

}
