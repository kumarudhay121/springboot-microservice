package com.kumarudhay121.orderservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumarudhay121.orderservice.dto.OrderLineItemsDto;
import com.kumarudhay121.orderservice.dto.OrderRequest;
import com.kumarudhay121.orderservice.model.Order;
import com.kumarudhay121.orderservice.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Testcontainers
@Slf4j
@AutoConfigureMockMvc
class OrderServiceApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.5");

    static {
        mysql.start();
    }

    @DynamicPropertySource
    public void setProperty(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", mysql::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mysql::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void shouldPlaceOrder() throws Exception {
        OrderRequest orderRequest = getOrderRequest();
        String orderRequestString = objectMapper.writeValueAsString(orderRequest);
        List<Order> order = orderRepository.findAll();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderRequestString))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        log.info(objectMapper.writeValueAsString(order.toArray()[0]));
    }

    private OrderRequest getOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();
        List<OrderLineItemsDto> orderLineItemsList = new ArrayList<OrderLineItemsDto>();
        orderLineItemsList.add(OrderLineItemsDto.builder()
                .skuCode("iphone_13")
                .price(BigDecimal.valueOf(2000))
                .quantity(3)
                .build());
        orderRequest.setOrderLineItemsDtoList(orderLineItemsList);
        return orderRequest;
    }

}
