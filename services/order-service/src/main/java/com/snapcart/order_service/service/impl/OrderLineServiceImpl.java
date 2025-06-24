package com.snapcart.order_service.service.impl;

import com.snapcart.order_service.constant.OrderLineEvent;
import com.snapcart.order_service.constant.OrderLineStatus;
import com.snapcart.order_service.dto.mapper.OrderLineMapper;
import com.snapcart.order_service.dto.request.FilterOrderLineRequest;
import com.snapcart.order_service.dto.request.UpdateOrderLineStatusRequest;
import com.snapcart.order_service.dto.response.OrderLineResponse;
import com.snapcart.order_service.entity.OrderEntity;
import com.snapcart.order_service.entity.OrderLineEntity;
import com.snapcart.order_service.repository.OrderLineRepository;
import com.snapcart.order_service.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    private final StateMachineFactory<OrderLineStatus, OrderLineEvent> stateMachineFactory;

    @Override
    public List<OrderLineResponse> filterOrderLine(FilterOrderLineRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "updatedAt"));
        List<OrderLineEntity> orderLineEntities = orderLineRepository.filterOrderLine(request, null, sort);
        return orderLineMapper.toModelList(orderLineEntities);
    }

    @Override
    public Page<OrderLineResponse> filterOrderLinePaged(FilterOrderLineRequest request, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "updatedAt"));
        List<OrderLineEntity> orderLineEntityPage = orderLineRepository.filterOrderLine(request, pageable, sort);
        long count = orderLineRepository.countOrderLine(request);
        List<OrderLineResponse> orderLineResponses = orderLineMapper.toModelList(orderLineEntityPage);
        return new PageImpl<>(orderLineResponses, pageable, count);
    }

    @Override
    public void sendEvent(String orderId, UpdateOrderLineStatusRequest request) {
        StateMachine<OrderLineStatus, OrderLineEvent> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.start();
        OrderLineEntity orderLineEntity = orderLineRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Not found any order with id =" + orderId));

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(access -> access.resetStateMachine(
                        new DefaultStateMachineContext<>(orderLineEntity.getStatus(), null, null, null)
                ));

        MessageBuilder<OrderLineEvent> messageBuilder = MessageBuilder.withPayload(request.getEvent())
                .setHeader("orderLineId", orderId);

        if (request.getEvent() == OrderLineEvent.CANCEL_ORDER && request.getCancelReason() != null) {
            messageBuilder.setHeader("cancelReason", request.getCancelReason());
        }

        boolean accepted = stateMachine.sendEvent(messageBuilder.build());
        if (!accepted) {
            throw new RuntimeException("Invalid order state");
        }
    }
}
