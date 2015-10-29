package dao;

/**
 * Created by DIego on 29/10/2015..
 */
public class LocalidadDao {
    private static LocalidadDao instance = new LocalidadDao();

    private LocalidadDao(){}

    public static LocalidadDao getInstance(){
        return instance;
    }
}
