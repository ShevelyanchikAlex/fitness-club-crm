package com.shevelyanchik.fitnessclub.orderservice.repository;

import com.shevelyanchik.fitnessclub.orderservice.model.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
