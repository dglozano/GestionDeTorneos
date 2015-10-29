package dao;

/**
 * Created by DIego on 29/10/2015..
 */
public class LugarDeRealizacionDao {

    private static LugarDeRealizacionDao  instance = new LugarDeRealizacionDao ();

    private LugarDeRealizacionDao (){}

    public static LugarDeRealizacionDao  getInstance(){
        return instance;
    }
}


