package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/services")
public class ServiceController {
    private final ServiceService serviceService;

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PostMapping("/create")
    public ResponseEntity<ServiceDto> createService(@Valid @RequestBody ServiceDto serviceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.createService(serviceDto));
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @PatchMapping("/update")
    public ResponseEntity<ServiceDto> updateService(@Valid @RequestBody ServiceDto updatedServiceDto) {
        return ResponseEntity.ok(serviceService.updateService(updatedServiceDto));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<ServiceDto>> findAllServices(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<ServiceDto> serviceDtoPage = serviceService.findAllServices(PageRequest.of(page, size));
        return ResponseEntity.ok(serviceDtoPage.getContent());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    @Cacheable(value = "services", key = "#id")
    public ResponseEntity<ServiceDto> findServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.findServiceById(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public ResponseEntity<Long> countServices() {
        return ResponseEntity.ok(serviceService.countServices());
    }

}
