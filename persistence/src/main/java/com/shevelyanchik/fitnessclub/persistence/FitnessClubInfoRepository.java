package com.shevelyanchik.fitnessclub.persistence;

import com.shevelyanchik.fitnessclub.model.domain.FitnessClubInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessClubInfoRepository extends JpaRepository<FitnessClubInfo, Long> {
}
