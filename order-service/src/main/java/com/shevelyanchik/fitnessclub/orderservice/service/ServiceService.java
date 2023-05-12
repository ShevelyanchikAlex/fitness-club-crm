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
     * Creates new Service.
     *
     * @param serviceDto ServiceDto that contains all information about Service.
     * @return Service.
     */
    ServiceDto createService(ServiceDto serviceDto);

    /**
     * If the service exists, this method will return a Service that contains information about them.
     *
     * @param id Service id.
     * @return Service if the service exists, throws ServiceException otherwise.
     */
    ServiceDto findServiceById(Long id);

    /**
     * Returns all Services.
     *
     * @param pageable Pageable that is needed for pagination.
     * @return Services.
     */
    Page<ServiceDto> findAllServices(Pageable pageable);

    /**
     * Deletes all Services.
     */
    void deleteAllServices();
}
