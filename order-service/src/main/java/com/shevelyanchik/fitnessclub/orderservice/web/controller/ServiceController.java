package com.shevelyanchik.fitnessclub.orderservice.web.controller;

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
@RequestMapping("/api/v1/services")
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping
    public ServiceDto save(@RequestBody ServiceDto serviceDto) {
        return serviceService.save(serviceDto);
    }

    @GetMapping
    public List<ServiceDto> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ServiceDto> serviceDtoPage = serviceService.findAll(PageRequest.of(page, size));
        return new ArrayList<>(serviceDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public ServiceDto findById(@PathVariable Long id) {
        return serviceService.findById(id);
    }
}
