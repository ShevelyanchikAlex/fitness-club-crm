package com.shevelyanchik.fitnessclub.orderservice.integration.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ServiceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ServiceService serviceService;

    private final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(1L, "Service", "Service desc", BigDecimal.ONE);

    private final String SERVICE_API_ENDPOINT = "/api/v1/order-service/services";


    @AfterEach
    void deleteServices() {
        serviceService.deleteAllServices();
    }

    @Test
    void testCreateService() {
        //when
        ResponseEntity<ServiceDto> response = testRestTemplate.postForEntity(
                SERVICE_API_ENDPOINT + "/create", EXPECTED_SERVICE_DTO, ServiceDto.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getName(), response.getBody().getName());
    }

    @Test
    @Disabled
    void testFindServiceById() {
        //when
        ResponseEntity<ServiceDto> responseWithSavedService = testRestTemplate.postForEntity(
                SERVICE_API_ENDPOINT, EXPECTED_SERVICE_DTO, ServiceDto.class);
        ResponseEntity<ServiceDto> response = testRestTemplate.getForEntity(SERVICE_API_ENDPOINT + "/{id}",
                ServiceDto.class, Objects.requireNonNull(responseWithSavedService.getBody()).getId());
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getName(), response.getBody().getName());
    }

}
