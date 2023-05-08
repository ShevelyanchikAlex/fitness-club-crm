package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.orderservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapper;
import com.shevelyanchik.fitnessclub.orderservice.persistence.ServiceRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        com.shevelyanchik.fitnessclub.orderservice.model.entity.Service service = serviceMapper.toEntity(serviceDto);
        com.shevelyanchik.fitnessclub.orderservice.model.entity.Service savedService = serviceRepository.save(service);
        return serviceMapper.toDto(savedService);
    }

    @Override
    public ServiceDto findServiceById(Long id) {
        return serviceRepository
                .findById(id)
                .map(serviceMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + id));
    }

    @Override
    public Page<ServiceDto> findAllServices(Pageable pageable) {
        List<ServiceDto> serviceDtoList = serviceRepository
                .findAll(pageable)
                .stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(serviceDtoList, pageable, serviceRepository.count());
    }
}
