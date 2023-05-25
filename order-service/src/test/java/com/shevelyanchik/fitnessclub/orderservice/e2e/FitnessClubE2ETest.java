package com.shevelyanchik.fitnessclub.orderservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FitnessClubE2ETest {

    private static final FitnessClubInfoDto EXPECTED_FITNESS_CLUB_INFO_DTO = new FitnessClubInfoDto(
            1L, "Address", "Description");
    private static final String BASE_API_URL = "http://localhost:8765/api/v1";
    private static final String FITNESS_CLUB_INFO_API_ENDPOINT = "/order-service/fitness-club-info";

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
    void testCreateFitnessClubInfo() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<FitnessClubInfoDto> request = new HttpEntity<>(EXPECTED_FITNESS_CLUB_INFO_DTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                FITNESS_CLUB_INFO_API_ENDPOINT, request, String.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        FitnessClubInfoDto fitnessClubInfoDtoResponse = objectMapper.readValue(response.getBody(), FitnessClubInfoDto.class);
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getAddress(), fitnessClubInfoDtoResponse.getAddress());
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription(), fitnessClubInfoDtoResponse.getDescription());
    }

    @Test
    @Order(2)
    @SneakyThrows
    void testFindFitnessClubInfoById() {
        //given
        long expectedId = EXPECTED_FITNESS_CLUB_INFO_DTO.getId();
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<Long> request = new HttpEntity<>(expectedId, headers);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                FITNESS_CLUB_INFO_API_ENDPOINT + "/{id}", String.class, request);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        FitnessClubInfoDto fitnessClubInfoDtoResponse = objectMapper.readValue(response.getBody(), FitnessClubInfoDto.class);
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getAddress(), fitnessClubInfoDtoResponse.getAddress());
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription(), fitnessClubInfoDtoResponse.getDescription());
    }

}
