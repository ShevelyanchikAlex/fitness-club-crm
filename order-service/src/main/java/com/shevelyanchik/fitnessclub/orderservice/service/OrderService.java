package com.shevelyanchik.fitnessclub.orderservice.service;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The OrderService provides the ability to create new Order and retrieving information about them.
 *
 * @version 1.0.1
 */
public interface OrderService {
    /**
     * Creates new Order.
     *
     * @param orderDto OrderDto that contains all information about Order.
     * @return Order.
     */
    OrderDto createOrder(OrderDto orderDto);

    /**
     * If the order exists, this method will return an Order that contains information about them.
     *
     * @param id Order id.
     * @return Order if the order exists, throws ServiceException otherwise.
     */
    OrderDto findOrderById(Long id);

    /**
     * If the order exists, this method will return an Order that contains information about them
     * (includes full info about User and Trainer).
     *
     * @param id Order id.
     * @return Order (includes full info about User and Trainer) if the order exists, throws ServiceException otherwise.
     */
    OrderResponseDto findOrderByIdWithUsersInfo(Long id);

    /**
     * Returns all Orders.
     *
     * @param pageable Pageable that is needed for pagination.
     * @return Orders.
     */
    Page<OrderDto> findAllOrders(Pageable pageable);
}
