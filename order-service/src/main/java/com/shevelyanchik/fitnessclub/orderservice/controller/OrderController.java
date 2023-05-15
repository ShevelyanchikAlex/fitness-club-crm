package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/orders")
public class OrderController {
    private final OrderService orderService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @GetMapping
    public List<OrderDto> findAllOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<OrderDto> orderDtoPage = orderService.findAllOrders(PageRequest.of(page, size));
        return orderDtoPage.getContent();
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/user/{userId}")
    public List<OrderDto> findAllOrdersByUserId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size, @PathVariable Long userId) {
        Page<OrderDto> orderDtoPage = orderService.findAllOrdersByUserId(PageRequest.of(page, size), userId);
        return orderDtoPage.getContent();
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/{id}")
    public OrderDto findOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/full-order/{id}")
    public OrderResponseDto findOrderByIdWithUsersInfo(@PathVariable Long id) {
        return circuitBreakerFactory.create("full-order").run(
                () -> orderService.findOrderByIdWithUsersInfo(id),
                throwable -> new OrderResponseDto());
    }
}
