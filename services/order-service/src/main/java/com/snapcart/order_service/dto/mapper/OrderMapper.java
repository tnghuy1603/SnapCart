package com.snapcart.order_service.dto.mapper;

import com.snapcart.order_service.dto.response.OrderResponse;
import com.snapcart.order_service.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toModel(OrderEntity entity);
}
