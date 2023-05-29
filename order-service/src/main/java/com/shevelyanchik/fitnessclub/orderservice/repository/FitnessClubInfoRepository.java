package com.shevelyanchik.fitnessclub.orderservice.repository;

import com.shevelyanchik.fitnessclub.orderservice.model.entity.FitnessClubInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessClubInfoRepository extends JpaRepository<FitnessClubInfo, Long> {
}
