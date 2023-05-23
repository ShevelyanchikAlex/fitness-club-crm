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
     * Creates a new fitness club information with the provided data.
     *
     * @param fitnessClubInfoDto The FitnessClubInfoDto containing the fitness club information.
     * @return The FitnessClubInfoDto representing the created fitness club information.
     */
    FitnessClubInfoDto createFitnessClubInfo(FitnessClubInfoDto fitnessClubInfoDto);

    /**
     * Finds a fitness club information by its ID.
     *
     * @param id The ID of the fitness club information to find.
     * @return The FitnessClubInfoDto representing the found fitness club information.
     */
    FitnessClubInfoDto findFitnessClubInfoById(Long id);

    /**
     * Retrieves a paginated list of all fitness club information.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A page of fitness club information DTOs.
     */
    Page<FitnessClubInfoDto> findAllFitnessClubInfos(Pageable pageable);

    /**
     * Deletes all fitness club information.
     */
    void deleteAllFitnessClubInfos();
}
