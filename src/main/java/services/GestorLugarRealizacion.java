package services;

import dao.LugarDeRealizacionDao;
import models.LugarDeRealizacion;
import models.UsuarioLogueado;

import java.util.List;

/**
 * Created by DIego on 2/11/2015..
 */
public class GestorLugarRealizacion {
    private LugarDeRealizacionDao lugarDeRealizacionDao = LugarDeRealizacionDao.getInstance();
    private UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();

    /*public List<LugarDeRealizacion> buscarLugaresDelUsuario(String deporte){
        int idUsuario = usuarioLogueado.getUsuarioLogueado().getId();

    }*/
}
