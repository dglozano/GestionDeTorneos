package models;

/**
 * Created by DIego on 26/10/2015.
 */

public class UsuarioLogueado {

    private static UsuarioLogueado instance = new UsuarioLogueado();
    private Usuario usuarioLogueado;

    private UsuarioLogueado(){}

    public UsuarioLogueado getInstance(){
        return instance;
    }

    public Usuario getUsuarioLogueado(){
        return usuarioLogueado;
    }

}
