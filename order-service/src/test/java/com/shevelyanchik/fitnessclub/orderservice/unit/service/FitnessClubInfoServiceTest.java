package com.shevelyanchik.fitnessclub.orderservice.unit.service;

import com.shevelyanchik.fitnessclub.orderservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.FitnessClubInfoMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.FitnessClubInfoMapperImpl;
import com.shevelyanchik.fitnessclub.orderservice.repository.FitnessClubInfoRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.impl.FitnessClubInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FitnessClubInfoServiceTest {

    private static final FitnessClubInfoDto EXPECTED_FITNESS_CLUB_INFO_DTO = new FitnessClubInfoDto(
            1L, "Address", "Description");

    @InjectMocks
    private FitnessClubInfoServiceImpl fitnessClubInfoService;

    @Mock
    private FitnessClubInfoRepository fitnessClubInfoRepository;

    @Spy
    private final FitnessClubInfoMapper fitnessClubInfoMapper = new FitnessClubInfoMapperImpl();


    @Test
    void testCreateFitnessClubInfo() {
        //given
        FitnessClubInfo expectedFitnessClubInfo = fitnessClubInfoMapper.toEntity(EXPECTED_FITNESS_CLUB_INFO_DTO);
        BDDMockito.given(fitnessClubInfoRepository.save(any())).willReturn(expectedFitnessClubInfo);
        //when
        FitnessClubInfoDto actualFitnessClubInfoDto = fitnessClubInfoService.createFitnessClubInfo(EXPECTED_FITNESS_CLUB_INFO_DTO);
        //then
        BDDMockito.then(fitnessClubInfoRepository).should().save(any());
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO, actualFitnessClubInfoDto);
    }

    @Test
    void testFindFitnessClubInfoById() {
        //given
        FitnessClubInfo expectedFitnessClubInfo = fitnessClubInfoMapper.toEntity(EXPECTED_FITNESS_CLUB_INFO_DTO);
        BDDMockito.given(fitnessClubInfoRepository.findById(any())).willReturn(Optional.ofNullable(expectedFitnessClubInfo));
        //when
        FitnessClubInfoDto actualFitnessClubInfoDto = fitnessClubInfoService.findFitnessClubInfoById(1L);
        //then
        BDDMockito.then(fitnessClubInfoRepository).should().findById(any());
        Assertions.assertEquals(EXPECTED_FITNESS_CLUB_INFO_DTO, actualFitnessClubInfoDto);
    }

    @Test
    void testFindFitnessClubInfoByIdWithUnExistingId() {
        //given
        long expectedId = 1L;
        BDDMockito.given(fitnessClubInfoRepository.findById(any())).willThrow(EntityNotFoundException.class);
        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> fitnessClubInfoService.findFitnessClubInfoById(expectedId));
        BDDMockito.then(fitnessClubInfoRepository).should().findById(expectedId);
    }

    @Test
    void testFindAllFitnessClubInfos() {
        //given
        FitnessClubInfo expectedFitnessClubInfo = fitnessClubInfoMapper.toEntity(EXPECTED_FITNESS_CLUB_INFO_DTO);
        List<FitnessClubInfo> expectedFitnessClubInfoList = new ArrayList<>(List.of(expectedFitnessClubInfo));
        long expectedFitnessClubInfosCount = 1L;
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<FitnessClubInfo> expectedPage = new PageImpl<>(expectedFitnessClubInfoList, expectedPageable, 1L);

        BDDMockito.given(fitnessClubInfoRepository.count()).willReturn(expectedFitnessClubInfosCount);
        BDDMockito.given(fitnessClubInfoRepository.findAll(expectedPageable)).willReturn(expectedPage);
        //when
        Page<FitnessClubInfoDto> actualFitnessClubInfoDtoPage = fitnessClubInfoService.findAllFitnessClubInfos(expectedPageable);
        //then
        BDDMockito.then(fitnessClubInfoRepository).should().count();
        BDDMockito.then(fitnessClubInfoRepository).should().findAll(expectedPageable);
        Assertions.assertEquals(expectedFitnessClubInfoList.size(), actualFitnessClubInfoDtoPage.getTotalElements());
    }

}