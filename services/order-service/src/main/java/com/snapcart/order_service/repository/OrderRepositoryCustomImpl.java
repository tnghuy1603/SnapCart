package com.snapcart.order_service.repository;

import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.dto.response.OrderResponse;
import com.snapcart.order_service.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<OrderEntity> filterOrders(FilterOrderRequest request) {
        List<Criteria> criteriaList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getOrderId())) {
            criteriaList.add(Criteria.where("orderId").is(request.getOrderId()));
        }
        if (StringUtils.isNotBlank(request.getBuyerId())) {
            criteriaList.add(Criteria.where("buyerId").is(request.getBuyerId()));
        }
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        Pageable pageable = PageRequest.of(request.getOffset() / request.getLimit(), request.getLimit());
        query.with(pageable);
        List<OrderEntity> orderEntities = mongoTemplate.find(query, OrderEntity.class);
        long count = mongoTemplate.count(query, OrderEntity.class);
        return new PageImpl<>(orderEntities, pageable, count);
    }
}
