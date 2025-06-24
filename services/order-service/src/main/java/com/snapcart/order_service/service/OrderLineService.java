package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.request.FilterOrderLineRequest;
import com.snapcart.order_service.dto.request.UpdateOrderLineStatusRequest;
import com.snapcart.order_service.dto.response.OrderLineResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderLineService {
    List<OrderLineResponse> filterOrderLine(FilterOrderLineRequest request);
    Page<OrderLineResponse> filterOrderLinePaged(FilterOrderLineRequest request, int limit, int offset);
    void sendEvent(String orderId, UpdateOrderLineStatusRequest request);
}
