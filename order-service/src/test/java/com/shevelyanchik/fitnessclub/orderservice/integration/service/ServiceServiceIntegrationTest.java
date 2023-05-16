package com.shevelyanchik.fitnessclub.orderservice.integration.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ServiceServiceIntegrationTest {

    private static final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(
            1L, "Service", "Service desc", BigDecimal.ONE);


    @Autowired
    private ServiceService serviceService;


    @AfterEach
    void deleteServices() {
        serviceService.deleteAllServices();
    }

    @Test
    void testCreateService() {
        //when
        ServiceDto actualServiceDto = serviceService.createService(EXPECTED_SERVICE_DTO);
        //then
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getName(), actualServiceDto.getName());
    }

    @Test
    void testFindServiceById() {
        //when
        ServiceDto savedServiceDto = serviceService.createService(EXPECTED_SERVICE_DTO);
        ServiceDto actualServiceDto = serviceService.findServiceById(savedServiceDto.getId());
        //then
        Assertions.assertNotNull(actualServiceDto);
    }

    @Test
    void testFindAllServices() {
        //given
        List<ServiceDto> expectedServiceDtoList = new ArrayList<>(List.of(EXPECTED_SERVICE_DTO));
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<ServiceDto> expectedPage = new PageImpl<>(expectedServiceDtoList, expectedPageable, 1L);
        //when
        serviceService.createService(EXPECTED_SERVICE_DTO);
        Page<ServiceDto> actualPage = serviceService.findAllServices(expectedPageable);
        //then
        Assertions.assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
    }

}
