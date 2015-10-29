package dao;

import dao.util.MiEntityManager;
import models.Competencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CompetenciaDao {

    private static CompetenciaDao instance = new CompetenciaDao();

    private CompetenciaDao(){}

    public static CompetenciaDao getInstance(){
        return instance;
    }

    public void crearCompetencia(Competencia competencia) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(competencia);
        tx.commit();
        em.close();
    }

    public void actualizarCompetencia(Competencia competencia) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(competencia);
        tx.commit();
        em.close();
    }

    public void borrarCompetencia(Competencia competencia) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        competencia.setEliminada(true);
        java.sql.Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
        competencia.setFechaEliminada(timeNow);
        em.merge(competencia);
        tx.commit();
        em.close();
    }

    public Competencia buscarCompetenciaPorId(Integer id) {
        EntityManager em = MiEntityManager.get();
        Competencia competencia = em.find(Competencia.class, id);
        em.close();
        return competencia;
    }

    public List<Competencia> buscarTodasCompetencias() {
        EntityManager em = MiEntityManager.get();
        List<Competencia> listaCompetencias = em.createQuery("SELECT c FROM Competencia c").getResultList();
        em.close();
        return listaCompetencias;
    }
}
