package com.shevelyanchik.fitnessclub.orderservice.persistence;

import com.shevelyanchik.fitnessclub.orderservice.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
