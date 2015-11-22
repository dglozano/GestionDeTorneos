package dao;

import dao.util.MiEntityManager;
import models.Deporte;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

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

    public void actualizarDeporte(Deporte deporte) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(deporte);
        tx.commit();
        em.close();
    }

    public List<Deporte> buscarDeportes() {
        EntityManager em = MiEntityManager.get();
        List<Deporte> listaDeportes = em.createQuery("SELECT d FROM Deporte d").getResultList();
        em.close();
        return listaDeportes;
    }

    public Deporte buscarDeporte(String nombreDeporte){
        EntityManager em = MiEntityManager.get();
        Deporte deporte = (Deporte) em.createQuery("SELECT d FROM Deporte d WHERE nom_deporte = '"+nombreDeporte+"'").getSingleResult();
        em.close();
        return deporte;
    }
}

