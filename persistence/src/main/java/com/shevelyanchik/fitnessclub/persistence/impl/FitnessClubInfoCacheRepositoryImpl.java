package com.shevelyanchik.fitnessclub.persistence.impl;

import com.shevelyanchik.fitnessclub.model.domain.FitnessClubInfo;
import com.shevelyanchik.fitnessclub.persistence.FitnessClubInfoCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
public class FitnessClubInfoCacheRepositoryImpl implements FitnessClubInfoCacheRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void getDataWithFirstLevelCache() {
        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = session.beginTransaction();

        FitnessClubInfo fitnessClubInfo1 = session.load(FitnessClubInfo.class, 1L);
        log.info(fitnessClubInfo1.getId() + " " + fitnessClubInfo1.getAddress());

        FitnessClubInfo fitnessClubInfo2 = session.load(FitnessClubInfo.class, 1L);
        log.info(fitnessClubInfo2.getId() + " " + fitnessClubInfo2.getAddress());

        transaction.commit();
    }
}
