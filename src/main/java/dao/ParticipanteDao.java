package dao;

import dao.util.MiEntityManager;
import models.Participante;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by DIego on 29/10/2015..
 */
public class ParticipanteDao {
    private static ParticipanteDao instance = new ParticipanteDao();

    private ParticipanteDao(){}

    public static ParticipanteDao getInstance(){
        return instance;
    }

    public void crearParticipante(Participante participante) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(participante);
        tx.commit();
        em.close();
    }
}
