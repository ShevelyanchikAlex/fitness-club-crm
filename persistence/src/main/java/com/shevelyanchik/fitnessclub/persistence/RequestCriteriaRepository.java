package com.shevelyanchik.fitnessclub.persistence;

import com.shevelyanchik.fitnessclub.model.domain.request.Request;
import com.shevelyanchik.fitnessclub.model.domain.request.RequestStatus;

import java.util.List;

public interface RequestCriteriaRepository {
    List<Request> findAllByServiceName(String serviceName);

    List<Request> findAllByRequestStatus(RequestStatus requestStatus);

    List<Request> findAllByOptionalUserName(String userName);
}
