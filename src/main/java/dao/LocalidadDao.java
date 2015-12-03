package dao;

public class LocalidadDao {
    private static LocalidadDao instance = new LocalidadDao();

    private LocalidadDao(){}

    public static LocalidadDao getInstance(){
        return instance;
    }
}
