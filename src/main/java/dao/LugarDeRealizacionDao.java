package dao;

import dao.util.MiEntityManager;
import models.LugarDeRealizacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class LugarDeRealizacionDao {

    private static LugarDeRealizacionDao  instance = new LugarDeRealizacionDao ();

    private LugarDeRealizacionDao (){}

    public static LugarDeRealizacionDao  getInstance(){
        return instance;
    }

    public void crearLugar(LugarDeRealizacion lugarDeRealizacion) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(lugarDeRealizacion);
        tx.commit();
        em.close();
    }

    public List<LugarDeRealizacion> buscarLugaresDelUsuario(int idUsuario, int idDeporte) {
        EntityManager em = MiEntityManager.get();
        String sentencia = "SELECT l FROM LugarDeRealizacion l JOIN l.deportes d WHERE" +
                " id_usuario=:idUsuario AND d.id = :idDeporte";
        Query query = em.createQuery(sentencia);
        query.setParameter("idUsuario",idUsuario);
        query.setParameter("idDeporte",idDeporte);
        List<LugarDeRealizacion> listaLugares = query.getResultList();
        em.close();
        return listaLugares;
    }

    public LugarDeRealizacion buscarLugarPorNombre(int idUsuario, String nombreLugar) {
        EntityManager em = MiEntityManager.get();
        String sentencia = "SELECT l FROM LugarDeRealizacion l WHERE" +
                " id_usuario=:idUsuario AND l.nombre = :nombreLugar";
        Query query = em.createQuery(sentencia);
        query.setParameter("idUsuario",idUsuario);
        query.setParameter("nombreLugar",nombreLugar);
        LugarDeRealizacion lugar = (LugarDeRealizacion)query.getSingleResult();
        em.close();
        return lugar;
    }
}


