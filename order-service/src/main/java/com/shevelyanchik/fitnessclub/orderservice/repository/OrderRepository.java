package com.shevelyanchik.fitnessclub.orderservice.repository;

import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import com.shevelyanchik.fitnessclub.orderservice.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllOrdersByUserId(Pageable pageable, Long userId);

    Page<Order> findAllOrdersByTrainerId(Pageable pageable, Long trainerId);

    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.id = :id")
    void updateOrderStatusById(Long id, OrderStatus orderStatus);

    Long countAllOrdersByTrainerId(Long trainerId);

}
