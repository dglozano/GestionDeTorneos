package dao;

import dao.util.MiEntityManager;
import models.Deporte;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by DIego on 29/10/2015..
 */
public class DeporteDao {
    private static DeporteDao instance = new DeporteDao();

    private DeporteDao(){}

    public static DeporteDao getInstance(){
        return instance;
    }

    public void crearDeporte(Deporte deporte) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(deporte);
        tx.commit();
        em.close();
    }
}

