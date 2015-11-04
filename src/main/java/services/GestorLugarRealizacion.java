package services;

import dao.LugarDeRealizacionDao;
import models.Deporte;
import models.LugarDeRealizacion;
import models.UsuarioLogueado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 2/11/2015..
 */
public class GestorLugarRealizacion {
    private LugarDeRealizacionDao lugarDeRealizacionDao = LugarDeRealizacionDao.getInstance();
    private UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();

    public List<String> buscarLugaresDelUsuario(String deporteString){
        int idUsuario = usuarioLogueado.getUsuarioLogueado().getId();
        GestorDeporte gestorDeporte = new GestorDeporte();
        Deporte deporte = gestorDeporte.buscarDeporte(deporteString);
        List<LugarDeRealizacion> lugaresUsuario = lugarDeRealizacionDao.buscarLugaresDelUsuario(idUsuario, deporte.getId());
        List<String> listaLugaresString = new ArrayList<String>();
        for(LugarDeRealizacion lugar: lugaresUsuario){
            listaLugaresString.add(lugar.getNombre());
        }
        return listaLugaresString;
    }
}
