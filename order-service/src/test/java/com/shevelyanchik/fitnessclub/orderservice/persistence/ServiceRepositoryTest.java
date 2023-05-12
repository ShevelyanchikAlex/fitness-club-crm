package com.shevelyanchik.fitnessclub.orderservice.persistence;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Service;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper = new ServiceMapperImpl();

    private final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(1L, "Service", "Service desc", BigDecimal.ONE);


    @AfterEach
    void deleteServices() {
        serviceRepository.deleteAll();
    }


    @Test
    void testCreateService() {
        //given
        Service expectedService = serviceMapper.toEntity(EXPECTED_SERVICE_DTO);
        long expectedServicesCount = 1L;
        //when
        Service actualService = serviceRepository.save(expectedService);
        long actualServicesCount = serviceRepository.count();
        //then
        Assertions.assertEquals(expectedServicesCount, actualServicesCount);
        Assertions.assertEquals(EXPECTED_SERVICE_DTO.getName(), actualService.getName());
    }

    @Test
    void testFindServiceById() {
        //given
        Service expectedService = serviceMapper.toEntity(EXPECTED_SERVICE_DTO);
        //when
        Service savedService = serviceRepository.save(expectedService);
        Service actualService = serviceRepository.findById(savedService.getId()).orElse(null);
        //then
        Assertions.assertNotNull(actualService);
    }

    @Test
    void testFindAllServices() {
        //given
        Service expectedService = serviceMapper.toEntity(EXPECTED_SERVICE_DTO);
        long expectedServicesCount = 1L;
        //when
        serviceRepository.save(expectedService);
        List<Service> actualServiceList = serviceRepository.findAll();
        //then
        Assertions.assertEquals(expectedServicesCount, actualServiceList.size());
    }

}
