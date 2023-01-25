package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.persistence.FitnessClubInfoCacheRepository;
import com.shevelyanchik.fitnessclub.service.FitnessClubInfoCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FitnessClubInfoCacheServiceImpl implements FitnessClubInfoCacheService {
    private final FitnessClubInfoCacheRepository fitnessClubInfoRepository;

    @Override
    public void getDataWithFirstLevelCache() {
        fitnessClubInfoRepository.getDataWithFirstLevelCache();
    }

    @Override
    public void getDataWithSecondLevelCache() {
        fitnessClubInfoRepository.getDataWithSecondLevelCache();
    }
}
