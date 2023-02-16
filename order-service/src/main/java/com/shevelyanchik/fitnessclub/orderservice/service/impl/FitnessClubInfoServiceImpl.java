package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.orderservice.model.domain.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.FitnessClubInfoMapper;
import com.shevelyanchik.fitnessclub.orderservice.persistence.FitnessClubInfoRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
import com.shevelyanchik.fitnessclub.orderservice.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FitnessClubInfoServiceImpl implements FitnessClubInfoService {
    private static final String FITNESS_CLUB_INFO_NOT_EXIST = "fitness_club_info.not.exist";
    private final FitnessClubInfoRepository fitnessClubInfoRepository;
    private final FitnessClubInfoMapper fitnessClubInfoMapper;

    @Override
    public FitnessClubInfoDto createFitnessClubInfo(FitnessClubInfoDto fitnessClubInfoDto) {
        FitnessClubInfo entity = fitnessClubInfoMapper.toEntity(fitnessClubInfoDto);
        FitnessClubInfo save = fitnessClubInfoRepository.save(entity);
        return fitnessClubInfoMapper.toDto(save);
    }

    @Override
    public FitnessClubInfoDto findFitnessClubInfoById(Long id) {
        return fitnessClubInfoRepository
                .findById(id)
                .map(fitnessClubInfoMapper::toDto)
                .orElseThrow(() -> new ServiceException(FITNESS_CLUB_INFO_NOT_EXIST));
    }

    @Override
    public Page<FitnessClubInfoDto> findAllFitnessClubInfos(Pageable pageable) {
        List<FitnessClubInfoDto> fitnessClubInfoDtoList =
                fitnessClubInfoRepository
                        .findAll()
                        .stream()
                        .map(fitnessClubInfoMapper::toDto)
                        .collect(Collectors.toList());
        return new PageImpl<>(fitnessClubInfoDtoList, pageable, fitnessClubInfoRepository.count());
    }
}
