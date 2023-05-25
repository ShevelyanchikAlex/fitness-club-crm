package com.shevelyanchik.fitnessclub.orderservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderE2ETest {

    private static final LocalDateTime EXPECTED_DATE_TIME = LocalDateTime.now().plus(1L, ChronoUnit.DAYS);
    private static final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(
            1L, "Service", "Service desc", BigDecimal.ONE);
    private static final OrderDto EXPECTED_ORDER_DTO = new OrderDto(
            2L, EXPECTED_DATE_TIME, EXPECTED_DATE_TIME,
            1L, 1L, EXPECTED_SERVICE_DTO, OrderStatus.IN_PROCESSING);

    private static final String BASE_API_URL = "http://localhost:8765/api/v1";
    private static final String ORDER_API_ENDPOINT = "/order-service/orders";

    private TestRestTemplate testRestTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setupRestTemplate() {
        testRestTemplate = new TestRestTemplate(
                new RestTemplateBuilder()
                        .rootUri(BASE_API_URL)
        );
    }

    @Test
    @Order(1)
    @SneakyThrows
    void testCreateOrder() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<OrderDto> request = new HttpEntity<>(EXPECTED_ORDER_DTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                ORDER_API_ENDPOINT, request, String.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        OrderDto orderDtoResponse = objectMapper.readValue(response.getBody(), OrderDto.class);
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getOrderStatus(), orderDtoResponse.getOrderStatus());
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getUserId(), orderDtoResponse.getUserId());
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getTrainerId(), orderDtoResponse.getTrainerId());
    }

    @Test
    @Order(2)
    @SneakyThrows
    void testFindOrderById() {
        //given
        long expectedId = EXPECTED_SERVICE_DTO.getId();
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<Long> request = new HttpEntity<>(expectedId, headers);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                ORDER_API_ENDPOINT + "/{id}", String.class, request);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        OrderDto orderDtoResponse = objectMapper.readValue(response.getBody(), OrderDto.class);
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getOrderStatus(), orderDtoResponse.getOrderStatus());
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getUserId(), orderDtoResponse.getUserId());
        Assertions.assertEquals(EXPECTED_ORDER_DTO.getTrainerId(), orderDtoResponse.getTrainerId());
    }

    @Test
    @Order(3)
    @SneakyThrows
    void testCountOrders() {
        //given
        long expectedCount = 1L;
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<Long> request = new HttpEntity<>(expectedCount, headers);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                ORDER_API_ENDPOINT + "/count", String.class, request);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Long countOfOrders = objectMapper.readValue(response.getBody(), Long.class);
        Assertions.assertEquals(expectedCount, countOfOrders);
    }

}
