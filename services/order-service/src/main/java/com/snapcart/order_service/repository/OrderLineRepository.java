package com.snapcart.order_service.repository;

import com.snapcart.order_service.entity.OrderLineEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderLineRepository extends MongoRepository<OrderLineEntity, String>, OrderLineRepositoryCustom {
}
