package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.model.dto.FitnessClubInfoDto;
import com.shevelyanchik.fitnessclub.model.mapper.FitnessClubInfoMapper;
import com.shevelyanchik.fitnessclub.persistence.FitnessClubInfoRepository;
import com.shevelyanchik.fitnessclub.service.FitnessClubInfoService;
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
    public FitnessClubInfoDto save(FitnessClubInfoDto fitnessClubInfoDto) {
        return fitnessClubInfoMapper.toDto(fitnessClubInfoRepository.save(fitnessClubInfoMapper.toEntity(fitnessClubInfoDto)));
    }

    @Override
    public FitnessClubInfoDto findById(Long id) {
        return fitnessClubInfoMapper.toDto(fitnessClubInfoRepository.findById(id).get());
    }

    @Override
    public Page<FitnessClubInfoDto> findAll(Pageable pageable) {
        List<FitnessClubInfoDto> fitnessClubInfoDtoList = fitnessClubInfoRepository.findAll().stream()
                .map(fitnessClubInfoMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(fitnessClubInfoDtoList, pageable, fitnessClubInfoRepository.count());
    }
}
