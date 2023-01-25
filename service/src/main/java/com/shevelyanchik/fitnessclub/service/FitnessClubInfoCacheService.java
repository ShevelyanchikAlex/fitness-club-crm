package com.shevelyanchik.fitnessclub.service;

public interface FitnessClubInfoCacheService {
    void getDataWithFirstLevelCache();
    void getDataWithSecondLevelCache();
}
