package dao;

/**
 * Created by DIego on 29/10/2015..
 */
public class UsuarioDao {
    private static UsuarioDao instance = new UsuarioDao();

    private UsuarioDao(){}

    public static UsuarioDao getInstance(){
        return instance;
    }
}
