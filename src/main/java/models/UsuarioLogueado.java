package models;

public class UsuarioLogueado {

    private static UsuarioLogueado instance = new UsuarioLogueado();

    private Usuario usuarioLogueado;

    private UsuarioLogueado(){}

    public static UsuarioLogueado getInstance(){
        return instance;
    }

    public Usuario getUsuarioLogueado(){
        return usuarioLogueado;
    }

    public void setUsuarioLogueado (Usuario usuarioLogueado){
        this.usuarioLogueado = usuarioLogueado;
    }

}
