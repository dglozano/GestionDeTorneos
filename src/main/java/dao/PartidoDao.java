package dao;

import dao.util.MiEntityManager;
import models.Partido;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by DIego on 29/10/2015..
 */
public class PartidoDao {

    private static PartidoDao  instance = new PartidoDao ();

    private PartidoDao (){}

    public static PartidoDao  getInstance(){
        return instance;
    }

    public Partido buscarPartidoPorId(int id){
        EntityManager em = MiEntityManager.get();
        Partido partido = em.find(Partido.class, id);
        em.close();
        return partido;
    }

    public void actualizarPartido(Partido partido) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(partido);
        tx.commit();
        em.close();
    }
}
