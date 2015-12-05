package dao;

import dao.util.MiEntityManager;
import dtos.FiltrosCompetenciaDTO;
import models.Competencia;
import models.Fixture;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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

    public List<Competencia> buscarTodasCompetencias(int idUsuario) {
        EntityManager em = MiEntityManager.get();
        List<Competencia> listaCompetencias = em.createQuery("SELECT c FROM Competencia c WHERE id_usuario ="+idUsuario).getResultList();
        em.close();
        return listaCompetencias;
    }

    public List<Competencia> filtrarCompetencias(int idUsuario, FiltrosCompetenciaDTO filtros) {
        EntityManager em = MiEntityManager.get();
        String sentencia = "SELECT c FROM Competencia c";
        if(filtros.isFiltroDeporteActivo())
            sentencia+= " JOIN c.deporte d";
        sentencia+= " WHERE id_usuario=:idUsuario";
        if(filtros.isFiltroNombreActivo())
            sentencia+= " AND c.nombre LIKE :nombreCompetencia";
        if(filtros.isFiltroEstadoActivo())
            sentencia+= " AND c.estado = :estado";
        if(filtros.isFiltroModalidadActivo())
            sentencia+= " AND c.modalidad = :modalidad";
        if(filtros.isFiltroDeporteActivo())
            sentencia+= " AND d.nombre = :nombreDeporte";
        Query query = em.createQuery(sentencia);
        query.setParameter("idUsuario",idUsuario);
        if(filtros.isFiltroNombreActivo())
            query.setParameter("nombreCompetencia","%"+filtros.getNombre()+"%");
        if(filtros.isFiltroDeporteActivo())
            query.setParameter("nombreDeporte",filtros.getDeporte());
        if(filtros.isFiltroEstadoActivo())
            query.setParameter("estado",filtros.getEstado());
        if(filtros.isFiltroModalidadActivo())
            query.setParameter("modalidad",filtros.getModalidad());
        List<Competencia> listaCompetencias = query.getResultList();
        em.close();
        return listaCompetencias;
    }

    public void eliminarFixture(Fixture fixture){
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Fixture f=em.find(Fixture.class,fixture.getId());
        em.remove(f);
        tx.commit();
        em.close();
    }
}
