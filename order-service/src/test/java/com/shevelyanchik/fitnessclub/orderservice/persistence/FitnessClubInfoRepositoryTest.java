package com.shevelyanchik.fitnessclub.orderservice.persistence;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.FitnessClubInfoMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.FitnessClubInfoMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class FitnessClubInfoRepositoryTest {

    @Autowired
    private FitnessClubInfoRepository fitnessClubInfoRepository;

    private final FitnessClubInfoMapper fitnessClubInfoMapper = new FitnessClubInfoMapperImpl();


    private final FitnessClubInfoDto EXPECTED_FITNESS_CLUB_INFO_DTO = new FitnessClubInfoDto(
            1L, "Address", "Description");


    @AfterEach
    void deleteFitnessClubInfos() {
        fitnessClubInfoRepository.deleteAll();
    }

    @Test
    void testCreateFitnessClubInfo() {
        //given
        FitnessClubInfo expectedFitnessClubInfo = fitnessClubInfoMapper.toEntity(EXPECTED_FITNESS_CLUB_INFO_DTO);
        long expectedFitnessClubInfosCount = 1L;
        //when
        FitnessClubInfo actualFitnessClubInfo = fitnessClubInfoRepository.save(expectedFitnessClubInfo);
        long actualFitnessClubInfosCount = fitnessClubInfoRepository.count();
        //then
        Assertions.assertEquals(expectedFitnessClubInfosCount, actualFitnessClubInfosCount);
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO.getDescription(), actualFitnessClubInfo.getDescription());
    }

    @Test
    void testFindFitnessClubInfoById() {
        //given
        FitnessClubInfo expectedFitnessClubInfo = fitnessClubInfoMapper.toEntity(EXPECTED_FITNESS_CLUB_INFO_DTO);
        //when
        FitnessClubInfo savedFitnessClubInfo = fitnessClubInfoRepository.save(expectedFitnessClubInfo);
        FitnessClubInfo actualFitnessClubInfo = fitnessClubInfoRepository.findById(savedFitnessClubInfo.getId()).orElse(null);
        //then
        Assertions.assertNotNull(actualFitnessClubInfo);
    }

    @Test
    void testFindAllFitnessClubInfos() {
        //given
        FitnessClubInfo expectedFitnessClubInfo = fitnessClubInfoMapper.toEntity(EXPECTED_FITNESS_CLUB_INFO_DTO);
        long expectedFitnessClubInfosCount = 1L;
        //when
        fitnessClubInfoRepository.save(expectedFitnessClubInfo);
        List<FitnessClubInfo> actualFitnessClubInfoList = fitnessClubInfoRepository.findAll();
        //then
        Assertions.assertEquals(expectedFitnessClubInfosCount, actualFitnessClubInfoList.size());
    }

}
