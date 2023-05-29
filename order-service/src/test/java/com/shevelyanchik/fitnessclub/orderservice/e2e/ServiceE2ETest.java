package com.shevelyanchik.fitnessclub.orderservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceE2ETest {

    private static final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(
            1L, "Service", "Service desc", BigDecimal.ONE);
    private static final String BASE_API_URL = "http://localhost:8765/api/v1";
    private static final String SERVICE_API_ENDPOINT = "/order-service/services";

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
    void testCreateService() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<ServiceDto> request = new HttpEntity<>(EXPECTED_SERVICE_DTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                SERVICE_API_ENDPOINT + "/create", request, String.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        ServiceDto serviceDtoResponse = objectMapper.readValue(response.getBody(), ServiceDto.class);
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getName(), serviceDtoResponse.getName());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getDescription(), serviceDtoResponse.getDescription());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getPrice(), serviceDtoResponse.getPrice());
    }

    @Test
    @Order(2)
    @SneakyThrows
    void testFindServiceById() {
        //given
        long expectedId = EXPECTED_SERVICE_DTO.getId();
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<Long> requestHeaders = new HttpEntity<>(expectedId, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                SERVICE_API_ENDPOINT + "/{id}", HttpMethod.GET,
                requestHeaders, String.class, expectedId);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        ServiceDto serviceDtoResponse = objectMapper.readValue(response.getBody(), ServiceDto.class);
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getName(), serviceDtoResponse.getName());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getDescription(), serviceDtoResponse.getDescription());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getPrice(), serviceDtoResponse.getPrice());
    }

    @Test
    @Order(3)
    @SneakyThrows
    void testCountServices() {
        //given
        long expectedCount = 1L;
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<Long> requestHeaders = new HttpEntity<>(expectedCount, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                SERVICE_API_ENDPOINT + "/count", HttpMethod.GET,
                requestHeaders, String.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Long countOfServices = objectMapper.readValue(response.getBody(), Long.class);
        Assertions.assertEquals(expectedCount, countOfServices);
    }

}
