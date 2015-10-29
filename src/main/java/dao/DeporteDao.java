package dao;

/**
 * Created by DIego on 29/10/2015..
 */
public class DeporteDao {
    private static DeporteDao instance = new DeporteDao();

    private DeporteDao(){}

    public static DeporteDao getInstance(){
        return instance;
    }
}

