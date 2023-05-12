package com.shevelyanchik.fitnessclub.orderservice.integration.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FitnessClubInfoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FitnessClubInfoService fitnessClubInfoService;

    private final FitnessClubInfoDto EXPECTED_FITNESS_CLUB_INFO_DTO = new FitnessClubInfoDto(
            1L, "Address", "Description");

    private final String FITNESS_CLUB_INFO_API_ENDPOINT = "/api/v1/order-service/fitness-club-info";

    @AfterEach
    void deleteFitnessClubInfos() {
        fitnessClubInfoService.deleteAllFitnessClubInfos();
    }

    @Test
    void testCreateFitnessClubInfo() {
        //when
        ResponseEntity<FitnessClubInfoDto> response = testRestTemplate.postForEntity(
                FITNESS_CLUB_INFO_API_ENDPOINT, EXPECTED_FITNESS_CLUB_INFO_DTO, FitnessClubInfoDto.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription(), response.getBody().getDescription());
    }

    @Test
    void testFindFitnessClubInfoById() {
        //when
        ResponseEntity<FitnessClubInfoDto> responseWithSavedFitnessClubInfoDto = testRestTemplate.postForEntity(
                FITNESS_CLUB_INFO_API_ENDPOINT, EXPECTED_FITNESS_CLUB_INFO_DTO, FitnessClubInfoDto.class);
        ResponseEntity<FitnessClubInfoDto> response = testRestTemplate.getForEntity(FITNESS_CLUB_INFO_API_ENDPOINT + "/{id}",
                FitnessClubInfoDto.class, Objects.requireNonNull(responseWithSavedFitnessClubInfoDto.getBody()).getId());
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription(), response.getBody().getDescription());
    }

}
