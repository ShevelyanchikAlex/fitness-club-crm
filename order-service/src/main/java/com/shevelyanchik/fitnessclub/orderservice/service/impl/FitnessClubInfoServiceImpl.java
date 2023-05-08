package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.orderservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.FitnessClubInfoMapper;
import com.shevelyanchik.fitnessclub.orderservice.persistence.FitnessClubInfoRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.FitnessClubInfoService;
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
                .orElseThrow(() -> new EntityNotFoundException("FitnessClubInfo not found with id: " + id));
    }

    @Override
    public Page<FitnessClubInfoDto> findAllFitnessClubInfos(Pageable pageable) {
        List<FitnessClubInfoDto> fitnessClubInfoDtoList =
                fitnessClubInfoRepository
                        .findAll(pageable)
                        .stream()
                        .map(fitnessClubInfoMapper::toDto)
                        .collect(Collectors.toList());
        return new PageImpl<>(fitnessClubInfoDtoList, pageable, fitnessClubInfoRepository.count());
    }
}
