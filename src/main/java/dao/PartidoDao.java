package dao;

/**
 * Created by DIego on 29/10/2015..
 */
public class PartidoDao {
    private static PartidoDao  instance = new PartidoDao ();

    private PartidoDao (){}

    public static PartidoDao  getInstance(){
        return instance;
    }

}
