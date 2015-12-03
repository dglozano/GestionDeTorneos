package dao;

import dao.util.MiEntityManager;
import models.Partido;
import models.Resultado;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.List;

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
    @Transactional
    public void actualizarPartido(Partido partido) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(partido);
        tx.commit();
        em.close();
    }

    public void actualizarResultado(Resultado resultado) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(resultado);
        tx.commit();
        em.close();
    }

    public void eliminarResultadosVacios(){
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        List<Resultado> resultadosVacios = em.createQuery("SELECT r FROM Resultado r WHERE id_partido=null").getResultList();
        for(Resultado r: resultadosVacios){
            tx.begin();
            Resultado rborrar=em.find(Resultado.class,r.getId());
            em.remove(rborrar);
            tx.commit();
        }
        em.close();
    }
}
