package com.shevelyanchik.fitnessclub.orderservice.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FitnessClubInfoControllerIntegrationTest {

    private static final FitnessClubInfoDto EXPECTED_FITNESS_CLUB_INFO_DTO = new FitnessClubInfoDto(
            1L, "Address", "Description");

    private static final String FITNESS_CLUB_INFO_API_ENDPOINT = "/api/v1/order-service/fitness-club-info";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FitnessClubInfoService fitnessClubInfoService;


    @AfterEach
    void deleteFitnessClubInfos() {
        fitnessClubInfoService.deleteAllFitnessClubInfos();
    }

    @Test
    void testCreateFitnessClubInfo() throws Exception {
        HttpHeaders headers = initHeaders();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(FITNESS_CLUB_INFO_API_ENDPOINT)
                                .content(mapper.writeValueAsString(EXPECTED_FITNESS_CLUB_INFO_DTO))
                                .headers(headers)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription()));
    }

    @Test
    void testFindFitnessClubInfoById() throws Exception {
        //when
        FitnessClubInfoDto expectedFitnessClubInfoDto = initFitnessClubInfo();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(FITNESS_CLUB_INFO_API_ENDPOINT + "/{id}", expectedFitnessClubInfoDto.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value(expectedFitnessClubInfoDto.getDescription()));
    }


    private FitnessClubInfoDto initFitnessClubInfo() {
        return fitnessClubInfoService.createFitnessClubInfo(EXPECTED_FITNESS_CLUB_INFO_DTO);
    }

    private HttpHeaders initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("username", "test");
        headers.add("authorities", "ADMIN_PERMISSION");
        return headers;
    }

}
