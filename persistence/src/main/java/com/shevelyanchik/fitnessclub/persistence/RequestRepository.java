package com.shevelyanchik.fitnessclub.persistence;

import com.shevelyanchik.fitnessclub.model.domain.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, RequestCriteriaRepository {
}
