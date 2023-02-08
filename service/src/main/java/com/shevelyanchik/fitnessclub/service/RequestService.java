package com.shevelyanchik.fitnessclub.service;

import com.shevelyanchik.fitnessclub.model.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {
    RequestDto save(RequestDto requestDto);

    RequestDto findById(Long id);

    Page<RequestDto> findAll(Pageable pageable);

    List<RequestDto> findAllByServiceName(String serviceName);

    List<RequestDto> findAllByRequestStatus(String requestStatus);

    List<RequestDto> findAllByOptionalUserName(String userName);
}
