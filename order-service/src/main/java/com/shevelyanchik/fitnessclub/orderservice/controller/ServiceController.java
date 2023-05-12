package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/services")
public class ServiceController {
    private final ServiceService serviceService;

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto createService(@Valid @RequestBody ServiceDto serviceDto) {
        return serviceService.createService(serviceDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<ServiceDto> findAllServices(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ServiceDto> serviceDtoPage = serviceService.findAllServices(PageRequest.of(page, size));
        return serviceDtoPage.getContent();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    @Cacheable(value = "services", key = "#id")
    public ServiceDto findServiceById(@PathVariable Long id) {
        return serviceService.findServiceById(id);
    }
}
