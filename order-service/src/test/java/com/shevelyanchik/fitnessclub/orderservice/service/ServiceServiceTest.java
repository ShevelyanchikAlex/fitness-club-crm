package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Service;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapperImpl;
import com.shevelyanchik.fitnessclub.orderservice.persistence.ServiceRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @InjectMocks
    private ServiceServiceImpl serviceService;

    @Mock
    private ServiceRepository serviceRepository;

    @Spy
    private final ServiceMapper serviceMapper = new ServiceMapperImpl();

    private final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(1L, "Service", "Service desc", BigDecimal.ONE);

    @Test
    void testCreateService() {
        //given
        Service expectedService = serviceMapper.toEntity(EXPECTED_SERVICE_DTO);
        BDDMockito.given(serviceRepository.save(any())).willReturn(expectedService);
        //when
        ServiceDto actualServiceDto = serviceService.createService(EXPECTED_SERVICE_DTO);
        //then
        BDDMockito.then(serviceRepository).should().save(any());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO, actualServiceDto);
    }

    @Test
    void testFindServiceById() {
        //given
        Service expectedService = serviceMapper.toEntity(EXPECTED_SERVICE_DTO);
        BDDMockito.given(serviceRepository.findById(any())).willReturn(Optional.ofNullable(expectedService));
        //when
        ServiceDto actualServiceDto = serviceService.findServiceById(1L);
        //then
        BDDMockito.then(serviceRepository).should().findById(any());
        Assertions.assertEquals(EXPECTED_SERVICE_DTO, actualServiceDto);
    }

    @Test
    void testFindAllServices() {
        //given
        Service expectedService = serviceMapper.toEntity(EXPECTED_SERVICE_DTO);
        List<Service> expectedServiceList = new ArrayList<>(List.of(expectedService));
        long expectedServiceCount = 1L;
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<Service> expectedPage = new PageImpl<>(expectedServiceList, expectedPageable, 1L);

        BDDMockito.given(serviceRepository.count()).willReturn(expectedServiceCount);
        BDDMockito.given(serviceRepository.findAll(expectedPageable)).willReturn(expectedPage);
        //when
        Page<ServiceDto> actualPage = serviceService.findAllServices(expectedPageable);
        //then
        BDDMockito.then(serviceRepository).should().count();
        BDDMockito.then(serviceRepository).should().findAll(expectedPageable);
        Assertions.assertEquals(expectedServiceList.size(), actualPage.getTotalElements());
    }

}