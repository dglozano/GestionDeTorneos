package dao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MiEntityManager {
    private static EntityManagerFactory _entityManagerFactory = null;

    private static void inicializar(){
        if(_entityManagerFactory==null)
            _entityManagerFactory = Persistence.createEntityManagerFactory("taller");
    }

    public static EntityManager get(){
        inicializar();
        return _entityManagerFactory.createEntityManager();
    }
}
