package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto save(OrderDto orderDto);

    OrderDto findById(Long id);

    Page<OrderDto> findAll(Pageable pageable);
}
