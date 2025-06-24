package com.snapcart.order_service.controller;

import com.snapcart.order_service.dto.request.FilterOrderLineRequest;
import com.snapcart.order_service.dto.request.UpdateOrderLineStatusRequest;
import com.snapcart.order_service.dto.response.SnapCartResponse;
import com.snapcart.order_service.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order-lines")
@RequiredArgsConstructor
public class OrderLineController {
    private final OrderLineService orderLineService;
    @GetMapping
    public ResponseEntity<?> getOrderLines(FilterOrderLineRequest request,
                                           @RequestParam(name = "limit", required = false) Integer limit,
                                           @RequestParam(name = "offset", required = false) Integer offset) {
        if (limit != null && offset != null) {
            return SnapCartResponse.successListResponse(orderLineService.filterOrderLinePaged(request, limit, offset), null);
        }
        return SnapCartResponse.successResponse(orderLineService.filterOrderLine(request), null);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateOrderLineStatus(@PathVariable("id") String id, @RequestBody UpdateOrderLineStatusRequest request) {
        orderLineService.sendEvent(id, request);
        return SnapCartResponse.successResponse("Update order line status successfully");
    }
}
