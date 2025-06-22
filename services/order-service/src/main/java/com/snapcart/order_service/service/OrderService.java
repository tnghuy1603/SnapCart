package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.request.CheckOutRequest;
import com.snapcart.order_service.dto.request.FilterOrderRequest;
import com.snapcart.order_service.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CheckOutRequest request);
    List<OrderResponse> filterOrder(FilterOrderRequest request);
}
