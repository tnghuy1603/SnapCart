package com.snapcart.payment_service.repository;

import com.snapcart.payment_service.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentEntity, String> {
}
