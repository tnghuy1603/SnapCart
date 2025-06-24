package com.snapcart.order_service.repository;

import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.entity.OrderEntity;
import org.springframework.data.domain.Page;

public interface OrderRepositoryCustom {
    Page<OrderEntity> filterOrders(FilterOrderRequest request);
}
