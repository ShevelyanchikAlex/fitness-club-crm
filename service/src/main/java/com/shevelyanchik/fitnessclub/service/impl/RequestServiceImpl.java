package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.model.domain.request.Request;
import com.shevelyanchik.fitnessclub.model.dto.RequestDto;
import com.shevelyanchik.fitnessclub.model.mapper.RequestMapper;
import com.shevelyanchik.fitnessclub.persistence.RequestRepository;
import com.shevelyanchik.fitnessclub.service.RequestService;
import com.shevelyanchik.fitnessclub.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private static final String REQUEST_NOT_EXIST = "request.not.exist";
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public RequestDto save(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);
        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDto(savedRequest);
    }

    @Override
    public RequestDto findById(Long id) {
        return requestRepository
                .findById(id)
                .map(requestMapper::toDto)
                .orElseThrow(() -> new ServiceException(REQUEST_NOT_EXIST));
    }

    @Override
    public Page<RequestDto> findAll(Pageable pageable) {
        List<RequestDto> requestDtoList = requestRepository
                .findAll()
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(requestDtoList, pageable, requestRepository.count());
    }
}
