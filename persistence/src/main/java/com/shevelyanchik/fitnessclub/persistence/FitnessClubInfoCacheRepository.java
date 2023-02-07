package com.shevelyanchik.fitnessclub.persistence;

public interface FitnessClubInfoCacheRepository {
    void getDataWithFirstLevelCache();

    void getDataWithSecondLevelCache();
}
