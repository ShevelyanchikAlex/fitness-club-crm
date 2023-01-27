package com.shevelyanchik.fitnessclub.service.impl;

import com.shevelyanchik.fitnessclub.model.domain.request.Request;
import com.shevelyanchik.fitnessclub.model.domain.request.RequestStatus;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private static final String REQUEST_NOT_EXIST = "request.not.exist";
    private static final String REQUEST_STATUS_VALIDATION_ERROR = "request_status.validation.error";
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

    @Override
    @Transactional
    public List<RequestDto> findAllByServiceName(String serviceName) {
        return requestRepository.findAllByServiceName(serviceName)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> findAllByRequestStatus(String requestStatus) {
        try {
            RequestStatus requestStatusEnumVal = RequestStatus.valueOf(requestStatus.toUpperCase());
            return requestRepository.findAllByRequestStatus(requestStatusEnumVal)
                    .stream()
                    .map(requestMapper::toDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(REQUEST_STATUS_VALIDATION_ERROR);
        }
    }

    @Override
    public List<RequestDto> findAllByOptionalUserName(String userName) {
        return requestRepository.findAllByOptionalUserName(userName)
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }
}
