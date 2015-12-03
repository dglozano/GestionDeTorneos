package dao;

import dao.util.MiEntityManager;
import models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UsuarioDao {
    private static UsuarioDao instance = new UsuarioDao();

    private UsuarioDao(){}

    public static UsuarioDao getInstance(){
        return instance;
    }

    public void crearUsuario(Usuario usuario) {
        EntityManager em = MiEntityManager.get();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(usuario);
        tx.commit();
        em.close();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        EntityManager em = MiEntityManager.get();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }
}
