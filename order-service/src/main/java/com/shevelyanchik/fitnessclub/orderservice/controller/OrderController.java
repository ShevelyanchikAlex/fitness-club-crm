package com.shevelyanchik.fitnessclub.orderservice.controller;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-service/orders")
public class OrderController {

    private static final String CREATED_DATETIME_FIELD = "createdDateTime";

    private final OrderService orderService;
    private final CircuitBreakerFactory circuitBreakerFactory;


    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> findAllOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATETIME_FIELD).descending());
        Page<OrderDto> orderDtoPage = orderService.findAllOrders(pageable);
        return ResponseEntity.ok(orderDtoPage.getContent());
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> findAllOrdersByUserId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                @PathVariable Long userId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATETIME_FIELD).descending());
        Page<OrderDto> orderDtoPage = orderService.findAllOrdersByUserId(pageable, userId);
        return ResponseEntity.ok(orderDtoPage.getContent());
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<OrderDto>> findAllOrdersByTrainerId(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                   @PathVariable Long trainerId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATETIME_FIELD).descending());
        Page<OrderDto> orderDtoPage = orderService.findAllOrdersByTrainerId(pageable, trainerId);
        return ResponseEntity.ok(orderDtoPage.getContent());
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/full-order/{id}")
    public ResponseEntity<OrderResponseDto> findOrderByIdWithUsersInfo(@PathVariable Long id) {
        return ResponseEntity.ok(circuitBreakerFactory.create("full-order")
                .run(() -> orderService.findOrderByIdWithUsersInfo(id),
                        throwable -> new OrderResponseDto()));
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @PatchMapping("/update/{id}/order-status")
    public void updateOrderStatusById(@PathVariable Long id, @RequestParam("status") String status) {
        orderService.updateOrderStatusById(id, status);
    }

    @PreAuthorize("hasAuthority('ADMIN_PERMISSION')")
    @GetMapping("/count")
    public ResponseEntity<Long> countAllOrders() {
        return ResponseEntity.ok(orderService.countAllOrders());
    }

    @PreAuthorize("hasAuthority('TRAINER_PERMISSION')")
    @GetMapping("/count/trainerId/{trainerId}")
    public ResponseEntity<Long> countAllOrdersByTrainerId(@PathVariable Long trainerId) {
        return ResponseEntity.ok(orderService.countAllOrdersByTrainerId(trainerId));
    }

    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    @GetMapping("/count/userId/{userId}")
    public ResponseEntity<Long> countAllOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.countAllOrdersByUserId(userId));
    }

}
