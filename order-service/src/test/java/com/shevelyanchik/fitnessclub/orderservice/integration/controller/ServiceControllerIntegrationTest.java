package com.shevelyanchik.fitnessclub.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ServiceControllerIntegrationTest {

    private static final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(
            1L, "Service", "Service desc", BigDecimal.ONE);

    private static final String SERVICE_API_ENDPOINT = "/api/v1/order-service/services";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceService serviceService;


    @AfterEach
    void deleteServices() {
        serviceService.deleteAllServices();
    }

    @Test
    void testCreateService() throws Exception {
        HttpHeaders headers = initHeaders();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(SERVICE_API_ENDPOINT + "/create")
                                .content(mapper.writeValueAsString(EXPECTED_SERVICE_DTO))
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXPECTED_SERVICE_DTO.getName()));
    }

    @Test
    void testFindServiceById() throws Exception {
        ServiceDto savedService = initService();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(SERVICE_API_ENDPOINT + "/{id}", savedService.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(EXPECTED_SERVICE_DTO.getName()));
    }

    @Test
    void testCountServices() throws Exception {
        String expectedCount = "1";
        ServiceDto savedService = initService();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(SERVICE_API_ENDPOINT + "/count", savedService.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedCount));
    }

    private ServiceDto initService() {
        return serviceService.createService(EXPECTED_SERVICE_DTO);
    }

    private HttpHeaders initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username", "test");
        headers.add("authorities", "ADMIN_PERMISSION");
        return headers;
    }

}
