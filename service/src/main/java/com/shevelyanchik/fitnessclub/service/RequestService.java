package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {
    RequestDto save(RequestDto requestDto);

    RequestDto findById(Long id);

    Page<RequestDto> findAll(Pageable pageable);
}
