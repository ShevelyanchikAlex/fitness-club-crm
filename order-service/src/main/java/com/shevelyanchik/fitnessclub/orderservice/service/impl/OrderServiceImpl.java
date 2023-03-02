package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import com.shevelyanchik.fitnessclub.orderservice.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.orderservice.constant.EmailEventPayload;
import com.shevelyanchik.fitnessclub.orderservice.constant.OrderErrorMessageKey;
import com.shevelyanchik.fitnessclub.orderservice.model.domain.Order;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.OrderMapper;
import com.shevelyanchik.fitnessclub.orderservice.persistence.OrderRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderProducerService;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import com.shevelyanchik.fitnessclub.orderservice.service.exception.ServiceException;
import com.shevelyanchik.fitnessclub.orderservice.util.OrderEventUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceClient userServiceClient;
    private final OrderProducerService orderProducerService;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        OrderDto savedOrderDto = orderMapper.toDto(savedOrder);

        EmailEvent emailEvent = buildEmailEvent(savedOrderDto);
        orderProducerService.sendMessage(emailEvent);
        return savedOrderDto;
    }

    @Override
    public OrderDto findOrderById(Long id) {
        return orderRepository
                .findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ServiceException(OrderErrorMessageKey.ORDER_NOT_EXIST));
    }

    @Override
    public OrderResponseDto findOrderByIdWithUsersInfo(Long id) {
        OrderDto orderDto = orderRepository
                .findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ServiceException(OrderErrorMessageKey.ORDER_NOT_EXIST));
        UserDto userDto = userServiceClient.findUserById(orderDto.getUserId());
        TrainerDto trainerDto = userServiceClient.findTrainerById(orderDto.getTrainerId());
        return buildOrderResponseDto(orderDto, userDto, trainerDto);
    }

    @Override
    public Page<OrderDto> findAllOrders(Pageable pageable) {
        List<OrderDto> requestDtoList = orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(requestDtoList, pageable, orderRepository.count());
    }

    private OrderResponseDto buildOrderResponseDto(OrderDto orderDto, UserDto userDto, TrainerDto trainerDto) {
        return OrderResponseDto.builder()
                .id(orderDto.getId())
                .createdDateTime(orderDto.getCreatedDateTime())
                .trainingStartDateTime(orderDto.getTrainingStartDateTime())
                .userDto(userDto)
                .trainerDto(trainerDto)
                .service(orderDto.getService())
                .orderStatus(orderDto.getOrderStatus())
                .build();
    }

    private EmailEvent buildEmailEvent(OrderDto orderDto) {
        EmailEventPayload orderCreatedEventPayload = EmailEventPayload.ORDER_HAS_BEEN_CREATED;
        String message = OrderEventUtils.getEmailEventMessage(
                orderCreatedEventPayload,
                orderDto.getId(),
                orderDto.getUserId(),
                orderDto.getTrainerId(),
                orderDto.getService().getId(),
                orderDto.getOrderStatus()
        );

        return EmailEvent.builder()
                .subject(orderCreatedEventPayload.getSubject())
                .message(message)
                .build();
    }
}
