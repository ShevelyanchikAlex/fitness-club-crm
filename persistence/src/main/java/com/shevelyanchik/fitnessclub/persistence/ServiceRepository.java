package com.shevelyanchik.fitnessclub.persistence;

import com.shevelyanchik.fitnessclub.model.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
