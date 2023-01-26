package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.ServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceService {
    ServiceDto save(ServiceDto serviceDto);

    ServiceDto findById(Long id);

    Page<ServiceDto> findAll(Pageable pageable);
}
