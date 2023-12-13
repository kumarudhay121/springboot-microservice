package com.kumarudhay121.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kumarudhay121.orderservice.dto.OrderLineItemsDto;
import com.kumarudhay121.orderservice.dto.OrderRequest;
import com.kumarudhay121.orderservice.model.Order;
import com.kumarudhay121.orderservice.model.OrderLineItems;
import com.kumarudhay121.orderservice.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToOrderLineItems)
                .toList();

        order.setOrderLineItems(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderlineitemsdto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderlineitemsdto.getId());
        orderLineItems.setSkuCode(orderlineitemsdto.getSkuCode());
        orderLineItems.setPrice(orderlineitemsdto.getPrice());
        orderLineItems.setQuantity(orderlineitemsdto.getQuantity());
        return orderLineItems;
    }

}
