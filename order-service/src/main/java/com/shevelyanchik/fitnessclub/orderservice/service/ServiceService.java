package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The ServiceService provides the ability to create new fitness-club's Service and retrieving information about them.
 *
 * @version 1.0.1
 */
public interface ServiceService {
    /**
     * Creates a new service with the provided service data.
     *
     * @param serviceDto The ServiceDto object containing the service data.
     * @return The ServiceDto object representing the created service.
     */
    ServiceDto createService(ServiceDto serviceDto);

    /**
     * Updates an existing service with the provided updated service data.
     *
     * @param updatedServiceDto The ServiceDto object containing the updated service data.
     * @return The ServiceDto object representing the updated service.
     */
    ServiceDto updateService(ServiceDto updatedServiceDto);

    /**
     * Finds a service by its ID.
     *
     * @param id The ID of the service to find.
     * @return The ServiceDto object representing the found service.
     */
    ServiceDto findServiceById(Long id);

    /**
     * Retrieves all services in a pageable format.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A Page object containing the ServiceDto objects for the retrieved services.
     */
    Page<ServiceDto> findAllServices(Pageable pageable);

    /**
     * Counts the total number of services.
     *
     * @return The count of all services.
     */
    Long countServices();

    /**
     * Deletes all Services.
     */
    void deleteAllServices();

}
