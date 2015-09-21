package dao;

import dao.util.MiEntityManager;
import models.Competencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CompetenciaDao {
    public void crear(Competencia a) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        //em.getTransaction().begin();
        tx.begin();
        em.persist(a);
        tx.commit();
        em.close();
    }

}
