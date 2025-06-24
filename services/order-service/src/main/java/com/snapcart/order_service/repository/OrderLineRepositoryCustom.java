package com.snapcart.order_service.repository;

import com.snapcart.order_service.dto.request.FilterOrderLineRequest;
import com.snapcart.order_service.entity.OrderLineEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderLineRepositoryCustom {
    List<OrderLineEntity> filterOrderLine(FilterOrderLineRequest request, Pageable pageable, Sort sort);
    long countOrderLine(FilterOrderLineRequest request);
}
