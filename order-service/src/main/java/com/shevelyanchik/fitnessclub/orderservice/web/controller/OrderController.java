package com.shevelyanchik.fitnessclub.orderservice.web.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto save(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping
    public List<OrderDto> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<OrderDto> orderDtoPage = orderService.findAllOrders(PageRequest.of(page, size));
        return new ArrayList<>(orderDtoPage.getContent());
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping("/full-order/{id}")
    public OrderResponseDto findOrderByIdWithUsersData(@PathVariable Long id) {
        return orderService.findOrderByIdWithUsersInfo(id);
    }
}
