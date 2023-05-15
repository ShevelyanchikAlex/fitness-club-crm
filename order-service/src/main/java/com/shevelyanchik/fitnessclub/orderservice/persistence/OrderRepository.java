package com.shevelyanchik.fitnessclub.orderservice.persistence;

import com.shevelyanchik.fitnessclub.orderservice.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserId(Pageable pageable, Long userId);

}
