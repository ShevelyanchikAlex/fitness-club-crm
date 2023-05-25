package com.shevelyanchik.fitnessclub.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerIntegrationTest {

    private static final LocalDateTime EXPECTED_DATE_TIME = LocalDateTime.now().plus(1L, ChronoUnit.DAYS);

    private static final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(
            1L, "Service", "Service desc", BigDecimal.ONE);

    private static final OrderDto EXPECTED_ORDER_DTO = new OrderDto(
            1L, EXPECTED_DATE_TIME, EXPECTED_DATE_TIME,
            1L, 1L, EXPECTED_SERVICE_DTO, OrderStatus.IN_PROCESSING);

    private static final String ORDER_API_ENDPOINT = "/api/v1/order-service/orders";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ServiceService serviceService;


    @AfterEach
    void deleteOrders() {
        orderService.deleteAll();
    }

    @Test
    void testCreateOrder() throws Exception {
        ServiceDto savedServiceDto = serviceService.createService(EXPECTED_SERVICE_DTO);
        OrderDto expectedOrderDto = EXPECTED_ORDER_DTO;
        expectedOrderDto.setServiceDto(savedServiceDto);
        HttpHeaders headers = initHeaders();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(ORDER_API_ENDPOINT)
                                .content(mapper.writeValueAsString(expectedOrderDto))
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testFindOrderById() throws Exception {
        OrderDto expectedOrderDto = initOrder();
        HttpHeaders headers = initHeaders();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ORDER_API_ENDPOINT + "/{id}", expectedOrderDto.getId())
                                .headers(headers)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(expectedOrderDto.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainerId").value(expectedOrderDto.getTrainerId()));
    }

    private OrderDto initOrder() {
        ServiceDto savedServiceDto = serviceService.createService(EXPECTED_SERVICE_DTO);
        OrderDto expectedOrderDto = EXPECTED_ORDER_DTO;
        expectedOrderDto.setServiceDto(savedServiceDto);
        return orderService.createOrder(expectedOrderDto);
    }

    private HttpHeaders initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username", "test");
        headers.add("authorities", "USER_PERMISSION");
        return headers;
    }

}
