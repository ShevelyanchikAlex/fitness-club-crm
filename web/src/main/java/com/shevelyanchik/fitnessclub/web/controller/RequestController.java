package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.model.dto.RequestDto;
import com.shevelyanchik.fitnessclub.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/requests")
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public RequestDto save(@RequestBody RequestDto requestDto) {
        return requestService.save(requestDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public List<RequestDto> findAll(@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<RequestDto> requestDtoPage = requestService.findAll(PageRequest.of(pageIndex, size));
        return new ArrayList<>(requestDtoPage.getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public RequestDto findById(@PathVariable Long id) {
        return requestService.findById(id);
    }
}
