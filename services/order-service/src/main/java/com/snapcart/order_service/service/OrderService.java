package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.request.CheckOutRequest;
import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.dto.response.OrderResponse;
import org.springframework.data.domain.Page;


public interface OrderService {
    OrderResponse createOrder(CheckOutRequest request);
    Page<OrderResponse> filterOrder(FilterOrderRequest request);
}
