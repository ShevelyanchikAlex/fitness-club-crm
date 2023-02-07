package com.shevelyanchik.fitnessclub.persistence.impl;

import com.shevelyanchik.fitnessclub.model.domain.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.persistence.FitnessClubInfoCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
public class FitnessClubInfoCacheRepositoryImpl implements FitnessClubInfoCacheRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void getDataWithFirstLevelCache() {
        processTwoRequestsInOneSession();
    }

    @Override
    public void getDataWithSecondLevelCache() {
        processTwoRequestsInOneSession();
        processTwoRequestsInOneSession();
    }

    private void processTwoRequestsInOneSession() {
        Session mainSession = entityManager.unwrap(Session.class);
        SessionFactory sessionFactory = mainSession.getSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = mainSession.beginTransaction();

        FitnessClubInfo fitnessClubInfo1 = session.load(FitnessClubInfo.class, 1L);
        printLog(fitnessClubInfo1.getId(), fitnessClubInfo1.getAddress(), session.hashCode());

        FitnessClubInfo fitnessClubInfo2 = session.load(FitnessClubInfo.class, 1L);
        printLog(fitnessClubInfo2.getId(), fitnessClubInfo2.getAddress(), session.hashCode());

        transaction.commit();
        session.close();
    }

    private void printLog(long id, String address, int sessionHashCode) {
        log.info(id + " " + address + ", sessionHashCode: " + sessionHashCode);
    }
}
