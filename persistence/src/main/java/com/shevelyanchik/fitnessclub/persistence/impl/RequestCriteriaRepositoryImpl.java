package com.shevelyanchik.fitnessclub.persistence.impl;

import com.shevelyanchik.fitnessclub.model.domain.Service;
import com.shevelyanchik.fitnessclub.model.domain.request.Request;
import com.shevelyanchik.fitnessclub.model.domain.request.RequestStatus;
import com.shevelyanchik.fitnessclub.model.domain.user.User;
import com.shevelyanchik.fitnessclub.persistence.RequestCriteriaRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

public class RequestCriteriaRepositoryImpl implements RequestCriteriaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Request> findAllByServiceName(String serviceName) {
        Session mainSession = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = mainSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> request = cq.from(Request.class);

        Join<Request, Service> service = request.join("service", JoinType.LEFT);
        Predicate serviceNamePredicate = cb.equal(service.get("name"), serviceName);
        cq.select(request).where(serviceNamePredicate);
        TypedQuery<Request> query = entityManager.createQuery(cq);

        transaction.commit();
        session.close();
        return query.getResultList();
    }

    @Override
    public List<Request> findAllByRequestStatus(RequestStatus requestStatus) {
        Session mainSession = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = mainSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> request = cq.from(Request.class);

        Predicate requestStatusPredicate = cb.equal(request.get("requestStatus"), requestStatus);
        cq.select(request).where(requestStatusPredicate);
        TypedQuery<Request> query = entityManager.createQuery(cq);

        transaction.commit();
        session.close();
        return query.getResultList();
    }

    @Override
    public List<Request> findAllByOptionalUserName(String userName) {
        Session mainSession = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = mainSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Request> cq = cb.createQuery(Request.class);
        Root<Request> request = cq.from(Request.class);

        Join<Request, User> user = request.join("user", JoinType.LEFT);
        if (Objects.nonNull(userName) && !userName.isEmpty()) {
            Predicate userNamePredicate = cb.equal(user.get("name"), userName);
            cq.select(request).where(userNamePredicate);
        }
        TypedQuery<Request> query = entityManager.createQuery(cq);

        transaction.commit();
        session.close();
        return query.getResultList();
    }
}
