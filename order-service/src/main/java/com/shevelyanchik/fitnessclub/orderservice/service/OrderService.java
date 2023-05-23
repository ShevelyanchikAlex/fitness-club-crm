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
     * Creates a new order with the provided order data.
     *
     * @param orderDto The OrderDto object containing the order data.
     * @return The OrderDto object representing the created order.
     */
    OrderDto createOrder(OrderDto orderDto);

    /**
     * Updates the status of an order identified by its ID.
     *
     * @param id              The ID of the order to update.
     * @param orderStatusName The new status name for the order.
     */
    void updateOrderStatusById(Long id, String orderStatusName);

    /**
     * Finds an order by its ID.
     *
     * @param id The ID of the order to find.
     * @return The OrderDto object representing the found order.
     */
    OrderDto findOrderById(Long id);

    /**
     * Finds an order by its ID and includes additional user information.
     *
     * @param id The ID of the order to find.
     * @return The OrderResponseDto object representing the found order with user information.
     */
    OrderResponseDto findOrderByIdWithUsersInfo(Long id);

    /**
     * Retrieves all orders in a pageable format.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @return A Page object containing the OrderDto objects for the retrieved orders.
     */
    Page<OrderDto> findAllOrders(Pageable pageable);

    /**
     * Retrieves all orders associated with a specific user ID in a pageable format.
     *
     * @param pageable The pageable information for pagination and sorting.
     * @param userId   The ID of the user to retrieve orders for.
     * @return A Page object containing the OrderDto objects for the retrieved orders.
     */
    Page<OrderDto> findAllOrdersByUserId(Pageable pageable, Long userId);

    /**
     * Retrieves all orders associated with a specific trainer ID in a pageable format.
     *
     * @param pageable  The pageable information for pagination and sorting.
     * @param trainerId The ID of the trainer to retrieve orders for.
     * @return A Page object containing the OrderDto objects for the retrieved orders.
     */
    Page<OrderDto> findAllOrdersByTrainerId(Pageable pageable, Long trainerId);

    /**
     * Counts the total number of orders.
     *
     * @return The count of all orders.
     */
    Long countAllOrders();

    /**
     * Counts the total number of orders associated with a specific trainer ID.
     *
     * @param trainerId The ID of the trainer to count orders for.
     * @return The count of orders for the specified trainer.
     */
    Long countAllOrdersByTrainerId(Long trainerId);

    /**
     * Deletes all orders.
     */
    void deleteAll();

}
