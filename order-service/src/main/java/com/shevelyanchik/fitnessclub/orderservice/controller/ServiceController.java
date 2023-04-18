package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/services")
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping
    public ServiceDto createService(@RequestBody ServiceDto serviceDto) {
        return serviceService.createService(serviceDto);
    }

    @GetMapping
    public List<ServiceDto> findAllServices(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ServiceDto> serviceDtoPage = serviceService.findAllServices(PageRequest.of(page, size));
        return new ArrayList<>(serviceDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public ServiceDto findServiceById(@PathVariable Long id) {
        return serviceService.findServiceById(id);
    }
}
