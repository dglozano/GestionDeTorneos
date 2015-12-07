package services;

import dao.LugarDeRealizacionDao;
import models.Deporte;
import models.LugarDeRealizacion;
import models.UsuarioLogueado;

import java.util.ArrayList;
import java.util.List;

public class GestorLugarRealizacion {

    private LugarDeRealizacionDao lugarDeRealizacionDao = LugarDeRealizacionDao.getInstance();

    public List<String> buscarLugaresDelUsuario(String deporteString){
        int idUsuario = UsuarioLogueado.getInstance().getUsuarioLogueado().getId();
        GestorDeporte gestorDeporte = new GestorDeporte();
        Deporte deporte = gestorDeporte.buscarDeporte(deporteString);
        List<LugarDeRealizacion> lugaresUsuario = lugarDeRealizacionDao.buscarLugaresDelUsuario(idUsuario, deporte.getId());
        List<String> listaLugaresString = new ArrayList<String>();
        for(LugarDeRealizacion lugar: lugaresUsuario){
            listaLugaresString.add(lugar.getNombre());
        }
        return listaLugaresString;
    }

    public LugarDeRealizacion buscarLugarPorNombre(String nombreLugar){
        int idUsuario = UsuarioLogueado.getInstance().getUsuarioLogueado().getId();
        LugarDeRealizacion lugar = lugarDeRealizacionDao.buscarLugarPorNombre(idUsuario,nombreLugar);
        return lugar;
    }
}
