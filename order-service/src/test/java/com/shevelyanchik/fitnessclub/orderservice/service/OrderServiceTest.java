package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.ServiceDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.UserDto;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Order;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.OrderMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.OrderMapperImpl;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapper;
import com.shevelyanchik.fitnessclub.orderservice.model.mapper.ServiceMapperImpl;
import com.shevelyanchik.fitnessclub.orderservice.persistence.OrderRepository;
import com.shevelyanchik.fitnessclub.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private OrderProducerService orderProducerService;

    @Spy
    @InjectMocks
    private final OrderMapper orderMapper = new OrderMapperImpl();

    @Spy
    private final ServiceMapper serviceMapper = new ServiceMapperImpl();


    private final LocalDateTime EXPECTED_DATE_TIME = LocalDateTime.now();

    private final ServiceDto EXPECTED_SERVICE_DTO = new ServiceDto(1L, "Service", "Service desc", BigDecimal.ONE);

    private final OrderDto EXPECTED_ORDER_DTO = new OrderDto(
            1L, EXPECTED_DATE_TIME, EXPECTED_DATE_TIME,
            1L, 1L, EXPECTED_SERVICE_DTO, OrderStatus.IN_PROCESSING);

    private final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", "USER", "ACTIVE");

    private final TrainerDto EXPECTED_TRAINER_DTO = new TrainerDto(
            1L, "Higher", "Box", EXPECTED_USER_DTO);

    private final OrderResponseDto EXPECTED_ORDER_RESPONSE_DTO = new OrderResponseDto(
            1L, EXPECTED_DATE_TIME, EXPECTED_DATE_TIME, EXPECTED_USER_DTO, EXPECTED_TRAINER_DTO,
            EXPECTED_SERVICE_DTO, OrderStatus.IN_PROCESSING);


    @Test
    void testCreateOrder() {
        //given
        Order expectedOrder = orderMapper.toEntity(EXPECTED_ORDER_DTO);
        BDDMockito.given(orderRepository.save(any())).willReturn(expectedOrder);
        //when
        OrderDto actualOrderDto = orderService.createOrder(EXPECTED_ORDER_DTO);
        //then
        BDDMockito.then(orderRepository).should().save(any());
        Assertions.assertEquals(EXPECTED_ORDER_DTO, actualOrderDto);
    }

    @Test
    void testFindOrderById() {
        //given
        Order expectedOrder = orderMapper.toEntity(EXPECTED_ORDER_DTO);
        BDDMockito.given(orderRepository.findById(any())).willReturn(Optional.of(expectedOrder));
        //when
        OrderDto actualOrderDto = orderService.findOrderById(1L);
        //then
        BDDMockito.then(orderRepository).should().findById(any());
        Assertions.assertEquals(EXPECTED_ORDER_DTO, actualOrderDto);
    }

    @Test
    void testFindOrderByIdWithUsersInfo() {
        //given
        Order expectedOrder = orderMapper.toEntity(EXPECTED_ORDER_DTO);

        BDDMockito.given(userServiceClient.findUserById(any())).willReturn(EXPECTED_USER_DTO);
        BDDMockito.given(userServiceClient.findTrainerById(any())).willReturn(EXPECTED_TRAINER_DTO);
        BDDMockito.given(orderRepository.findById(any())).willReturn(Optional.of(expectedOrder));
        //when
        OrderResponseDto actualOrderResponseDto = orderService.findOrderByIdWithUsersInfo(1L);
        //then
        BDDMockito.then(orderRepository).should().findById(any());
        BDDMockito.then(userServiceClient).should().findUserById(any());
        BDDMockito.then(userServiceClient).should().findTrainerById(any());
        Assertions.assertEquals(EXPECTED_ORDER_RESPONSE_DTO, actualOrderResponseDto);
    }

    @Test
    void testFindAllOrders() {
        //given
        Order expectedOrder = orderMapper.toEntity(EXPECTED_ORDER_DTO);
        List<Order> expectedOrderList = new ArrayList<>(List.of(expectedOrder));
        long expectedOrdersCount = 1L;
        Pageable expectedPageable = PageRequest.of(0, 10);
        Page<Order> expectedPage = new PageImpl<>(expectedOrderList, expectedPageable, 1L);

        BDDMockito.given(orderRepository.count()).willReturn(expectedOrdersCount);
        BDDMockito.given(orderRepository.findAll(expectedPageable)).willReturn(expectedPage);
        //when
        Page<OrderDto> actualPage = orderService.findAllOrders(expectedPageable);
        //then
        BDDMockito.then(orderRepository).should().count();
        BDDMockito.then(orderRepository).should().findAll(expectedPageable);
        Assertions.assertEquals(expectedOrderList.size(), actualPage.getTotalElements());
    }

}