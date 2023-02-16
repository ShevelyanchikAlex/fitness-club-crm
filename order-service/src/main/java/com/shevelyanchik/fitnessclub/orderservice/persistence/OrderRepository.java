package com.shevelyanchik.fitnessclub.orderservice.persistence;

import com.shevelyanchik.fitnessclub.orderservice.model.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
