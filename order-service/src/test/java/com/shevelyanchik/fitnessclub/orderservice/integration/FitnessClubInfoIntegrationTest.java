package com.shevelyanchik.fitnessclub.orderservice.integration;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class FitnessClubInfoIntegrationTest {

    @Autowired
    private FitnessClubInfoService fitnessClubInfoService;

    private final FitnessClubInfoDto EXPECTED_FITNESS_CLUB_INFO_DTO = new FitnessClubInfoDto(
            1L, "Address", "Description");

    @AfterEach
    void deleteFitnessClubInfos() {
        fitnessClubInfoService.deleteAllFitnessClubInfos();
    }


    @Test
    void testCreateFitnessClubInfo() {
        //when
        FitnessClubInfoDto actualFitnessClubInfoDto = fitnessClubInfoService.createFitnessClubInfo(EXPECTED_FITNESS_CLUB_INFO_DTO);
        //then
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription(), actualFitnessClubInfoDto.getDescription());
    }

    @Test
    void testFindFitnessClubInfoById() {
        //when
        FitnessClubInfoDto savedFitnessClubInfoDto = fitnessClubInfoService.createFitnessClubInfo(EXPECTED_FITNESS_CLUB_INFO_DTO);
        FitnessClubInfoDto actualFitnessClubInfoDto = fitnessClubInfoService.findFitnessClubInfoById(savedFitnessClubInfoDto.getId());
        //then
        Assertions.assertNotNull(actualFitnessClubInfoDto);
    }

    @Test
    void testFindAllFitnessClubInfos() {
        //given
        List<FitnessClubInfoDto> expectedFitnessClubInfoDtoList = new ArrayList<>(List.of(EXPECTED_FITNESS_CLUB_INFO_DTO));
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<FitnessClubInfoDto> expectedPage = new PageImpl<>(expectedFitnessClubInfoDtoList, expectedPageable, 1L);
        //when
        fitnessClubInfoService.createFitnessClubInfo(EXPECTED_FITNESS_CLUB_INFO_DTO);
        Page<FitnessClubInfoDto> actualFitnessClubInfoPage = fitnessClubInfoService.findAllFitnessClubInfos(expectedPageable);
        //then
        Assertions.assertEquals(expectedPage.getTotalElements(), actualFitnessClubInfoPage.getTotalElements());
    }

}
