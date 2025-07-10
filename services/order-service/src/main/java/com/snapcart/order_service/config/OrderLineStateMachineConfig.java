package com.snapcart.order_service.config;

import com.snapcart.order_service.constant.OrderLineEvent;
import com.snapcart.order_service.constant.OrderLineStatus;
import com.snapcart.order_service.entity.OrderEntity;
import com.snapcart.order_service.dto.response.OrderStatusHistory;
import com.snapcart.order_service.entity.OrderLineEntity;
import com.snapcart.order_service.repository.OrderLineRepository;
import com.snapcart.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.time.LocalDateTime;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class OrderLineStateMachineConfig extends StateMachineConfigurerAdapter<OrderLineStatus, OrderLineEvent> {
    private final OrderLineRepository orderLineRepository;
    @Override
    public void configure(StateMachineStateConfigurer<OrderLineStatus, OrderLineEvent> states) throws Exception {
        states.withStates()
                .initial(OrderLineStatus.PROCESSING)
                .state(OrderLineStatus.FAIL)
                .state(OrderLineStatus.PENDING)
                .state(OrderLineStatus.CONFIRMED)
                .state(OrderLineStatus.SHIPPING)
                .state(OrderLineStatus.DELIVERED)
                .state(OrderLineStatus.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderLineStatus, OrderLineEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderLineStatus.PROCESSING).target(OrderLineStatus.PENDING).event(OrderLineEvent.PLACE_ORDER).action(logAction())
                .and()
                .withExternal().source(OrderLineStatus.PROCESSING).target(OrderLineStatus.FAIL).event(OrderLineEvent.FAIL_TO_PLACE).action(logAction())
                .and()
                .withExternal().source(OrderLineStatus.PENDING).target(OrderLineStatus.CONFIRMED).event(OrderLineEvent.CONFIRM_ORDER).action(logAction())
                .and()
                .withExternal().source(OrderLineStatus.PENDING).target(OrderLineStatus.CANCELLED).event(OrderLineEvent.CANCEL_ORDER).action(cancelAction())
                .and()
                .withExternal().source(OrderLineStatus.CONFIRMED).target(OrderLineStatus.PACKED).event(OrderLineEvent.PACK_ORDER).action(logAction())
                .and()
                .withExternal().source(OrderLineStatus.PACKED).target(OrderLineStatus.SHIPPING).event(OrderLineEvent.SHIP_ORDER).action(logAction())
                .and()
                .withExternal().source(OrderLineStatus.SHIPPING).target(OrderLineStatus.DELIVERED).event(OrderLineEvent.DELIVER_ORDER).action(logAction());

    }


    private Action<OrderLineStatus, OrderLineEvent> cancelAction() {
        return context -> {
            String orderLineId = (String) context.getMessageHeader("orderLineId");
            String cancelReason = (String) context.getMessageHeader("cancelReason");

            OrderLineEntity orderLine = orderLineRepository.findById(orderLineId)
                    .orElseThrow(() -> new RuntimeException("Order not found: " + orderLineId));

            OrderLineStatus fromStatus = context.getSource().getId();
            OrderLineStatus toStatus = context.getTarget().getId();
            OrderStatusHistory history = OrderStatusHistory.builder()
                    .fromStatus(fromStatus)
                    .toStatus(toStatus)
                    .timestamp(LocalDateTime.now())
                    .build();

            orderLine.getStatusHistory().add(history);

            orderLine.setCancelReason(cancelReason);
            orderLine.setStatus(OrderLineStatus.CANCELLED);
            orderLineRepository.save(orderLine);
            //TODO restore stock
            // productServiceClient.restoreStock(...);
        };
    }
    private Action<OrderLineStatus, OrderLineEvent> logAction() {
        return context -> {
            String orderLineId = (String) context.getMessageHeader("orderLineId");

            OrderLineEntity orderLineEntity = orderLineRepository.findById(orderLineId)
                    .orElseThrow(() -> new RuntimeException("Order not found: " + orderLineId));

            OrderLineStatus fromStatus = context.getSource().getId();
            OrderLineStatus toStatus = context.getTarget().getId();
            OrderStatusHistory history = OrderStatusHistory.builder()
                    .fromStatus(fromStatus)
                    .toStatus(toStatus)
                    .timestamp(LocalDateTime.now())
                    .build();
            orderLineEntity.getStatusHistory().add(history);
            orderLineEntity.setStatus(toStatus);
            orderLineRepository.save(orderLineEntity);
        };
    }


}
