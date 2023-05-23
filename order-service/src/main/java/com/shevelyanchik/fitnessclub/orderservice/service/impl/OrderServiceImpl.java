package com.shevelyanchik.fitnessclub.orderservice.service.impl;

import com.shevelyanchik.fitnessclub.kafkaconfig.dto.EmailEvent;
import com.shevelyanchik.fitnessclub.orderservice.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ScheduleDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Order;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.OrderMapper;
import com.shevelyanchik.fitnessclub.orderservice.persistence.OrderRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderProducerService;
import com.shevelyanchik.fitnessclub.orderservice.service.OrderService;
import com.shevelyanchik.fitnessclub.orderservice.service.ScheduleService;
import com.shevelyanchik.fitnessclub.orderservice.util.OrderEventUtils;
import com.shevelyanchik.fitnessclub.orderservice.util.OrderResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceClient userServiceClient;
    private final OrderProducerService orderProducerService;
    private final ScheduleService scheduleService;


    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderStatus(OrderStatus.IN_PROCESSING);
        Order order = orderMapper.toEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        OrderDto savedOrderDto = orderMapper.toDto(savedOrder);

        updateScheduleAvailableSpots(savedOrderDto.getTrainerId(), savedOrderDto.getTrainingStartDateTime());
        EmailEvent emailEvent = OrderEventUtils.createEmailEvent(savedOrderDto);
        orderProducerService.sendMessage(emailEvent);
        return savedOrderDto;
    }

    @Override
    @Transactional
    public void updateOrderStatusById(Long id, String orderStatusName) {
        try {
            OrderStatus orderStatus = OrderStatus.getOrderStatusByName(orderStatusName);
            orderRepository.updateOrderStatusById(id, orderStatus);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("OrderStatus not found with id: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto findOrderByIdWithUsersInfo(Long id) {
        OrderDto orderDto = orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        UserDto userDto = userServiceClient.findUserById(orderDto.getUserId());
        TrainerDto trainerDto = userServiceClient.findTrainerById(orderDto.getTrainerId());
        return OrderResponseUtils.createOrderResponseDto(orderDto, userDto, trainerDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> findAllOrders(Pageable pageable) {
        List<OrderDto> requestDtoList = orderRepository.findAll(pageable).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(requestDtoList, pageable, orderRepository.count());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> findAllOrdersByUserId(Pageable pageable, Long userId) {
        List<OrderDto> requestDtoList = orderRepository.findAllOrdersByUserId(pageable, userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(requestDtoList, pageable, orderRepository.count());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> findAllOrdersByTrainerId(Pageable pageable, Long trainerId) {
        List<OrderDto> requestDtoList = orderRepository.findAllOrdersByTrainerId(pageable, trainerId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(requestDtoList, pageable, orderRepository.count());
    }

    @Override
    public Long countAllOrders() {
        return orderRepository.count();
    }

    @Override
    public Long countAllOrdersByTrainerId(Long trainerId) {
        return orderRepository.countAllOrdersByTrainerId(trainerId);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    private void updateScheduleAvailableSpots(Long trainerId, LocalDateTime trainingStartDateTime) {
        ScheduleDto preUpdateScheduleDto = scheduleService.findScheduleByTrainerIdAndTrainingStartDateTime(
                trainerId, trainingStartDateTime);
        long updatedAvailableSpots = preUpdateScheduleDto.getAvailableSpots() - 1;
        scheduleService.updateScheduleAvailableSpotsById(preUpdateScheduleDto.getId(), updatedAvailableSpots);
    }

}
