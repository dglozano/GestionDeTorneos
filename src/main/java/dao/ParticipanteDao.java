package dao;

/**
 * Created by DIego on 29/10/2015..
 */
public class ParticipanteDao {
    private static ParticipanteDao instance = new ParticipanteDao();

    private ParticipanteDao(){}

    public static ParticipanteDao getInstance(){
        return instance;
    }
}
