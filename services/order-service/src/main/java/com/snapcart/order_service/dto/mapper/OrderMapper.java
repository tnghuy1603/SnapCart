package com.snapcart.order_service.dto.mapper;

import com.snapcart.order_service.dto.response.OrderResponse;
import com.snapcart.order_service.entity.OrderEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toModel(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(entity, orderResponse);
        return orderResponse;
    }

    public List<OrderResponse> toModelList(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
