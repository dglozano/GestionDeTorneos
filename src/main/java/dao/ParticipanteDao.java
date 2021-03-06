package dao;

import dao.util.MiEntityManager;
import models.Participante;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

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

    public void eliminarParticipante(Participante participante){
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Participante part=em.find(Participante.class,participante.getId());
        em.remove(part);
        tx.commit();
        em.close();
    }

    public void eliminarParticipantesSinCompetencia(){
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        List<Participante> listaParticipnate = em.createQuery("SELECT p FROM Participante p WHERE id_competencia=null").getResultList();
        for(Participante p: listaParticipnate){
            tx.begin();
            Participante pborrar=em.find(Participante.class,p.getId());
            em.remove(pborrar);
            tx.commit();
        }
        em.close();
    }

    public List<Participante> buscarParticipantes(int idCompetencia){
        EntityManager em = MiEntityManager.get();
        List<Participante> listaParticipantes = em.createQuery("SELECT p FROM Participante p WHERE id_competencia ="+idCompetencia).getResultList();
        em.close();
        return listaParticipantes;
    }

}
