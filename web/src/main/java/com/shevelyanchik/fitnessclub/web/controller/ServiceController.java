package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/services")
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public ServiceDto save(@RequestBody ServiceDto serviceDto) {
        return serviceService.save(serviceDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public List<ServiceDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ServiceDto> serviceDtoPage = serviceService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(serviceDtoPage.getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public ServiceDto findById(@PathVariable Long id) {
        return serviceService.findById(id);
    }
}
