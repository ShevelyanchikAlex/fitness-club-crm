package com.shevelyanchik.fitnessclub.orderservice.integration.controller;

import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderService orderService;


    private final LocalDateTime EXPECTED_DATE_TIME = LocalDateTime.now();

    private final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(1L, "Service", "Service desc", BigDecimal.ONE);

    private final OrderDto EXPECTED_ORDER_DTO = new OrderDto(
            1L, EXPECTED_DATE_TIME, EXPECTED_DATE_TIME,
            1L, 1L, EXPECTED_SERVICE_DTO, OrderStatus.IN_PROCESSING);

    private final String ORDER_API_ENDPOINT = "/api/v1/order-service/orders";

    @AfterEach
    void deleteOrders() {
        orderService.deleteAll();
    }

    @Test
    void testCreateOrder() {
        //when
        ResponseEntity<OrderDto> response = testRestTemplate.postForEntity(
                ORDER_API_ENDPOINT, EXPECTED_ORDER_DTO, OrderDto.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getOrderStatus(), response.getBody().getOrderStatus());
    }

}
