package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/orders")
public class OrderController {
    private final OrderService orderService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping
    public List<OrderDto> findAllOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<OrderDto> orderDtoPage = orderService.findAllOrders(PageRequest.of(page, size));
        return orderDtoPage.getContent();
    }

    @GetMapping("/{id}")
    public OrderDto findOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping("/full-order/{id}")
    public OrderResponseDto findOrderByIdWithUsersInfo(@PathVariable Long id) {
        return circuitBreakerFactory.create("full-order").run(
                () -> orderService.findOrderByIdWithUsersInfo(id),
                throwable -> new OrderResponseDto());
    }
}
