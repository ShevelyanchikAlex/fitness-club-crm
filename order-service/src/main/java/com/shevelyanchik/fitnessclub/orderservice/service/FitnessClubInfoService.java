package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.FitnessClubInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The FitnessClubInfoService provides the ability to create new FitnessClubInfo and retrieving information about them.
 *
 * @version 1.0.1
 */
public interface FitnessClubInfoService {
    /**
     * Creates new FitnessClubInfo.
     *
     * @param fitnessClubInfoDto FitnessClubInfoDto that contains all information about FitnessClubInfo.
     * @return FitnessClubInfo.
     */
    FitnessClubInfoDto createFitnessClubInfo(FitnessClubInfoDto fitnessClubInfoDto);

    /**
     * If the FitnessClubInfo exists, this method will return an FitnessClubInfo that contains information about them.
     *
     * @param id FitnessClubInfo id.
     * @return FitnessClubInfo if the FitnessClubInfo exists, throws ServiceException otherwise.
     */
    FitnessClubInfoDto findFitnessClubInfoById(Long id);

    /**
     * Returns FitnessClubInfos.
     *
     * @param pageable Pageable that is needed for pagination.
     * @return FitnessClubInfos.
     */
    Page<FitnessClubInfoDto> findAllFitnessClubInfos(Pageable pageable);
}
