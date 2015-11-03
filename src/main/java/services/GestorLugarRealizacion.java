package services;

import dao.LugarDeRealizacionDao;
import dtos.LugarYCodigoDTO;
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

    public List<LugarYCodigoDTO> buscarLugaresDelUsuario(String deporteString){
        int idUsuario = usuarioLogueado.getUsuarioLogueado().getId();
        GestorDeporte gestorDeporte = new GestorDeporte();
        Deporte deporte = gestorDeporte.buscarDeporte(deporteString);
        List<LugarDeRealizacion> lugaresUsuario = lugarDeRealizacionDao.buscarLugaresDelUsuario(idUsuario, deporte.getId());
        List<LugarYCodigoDTO> listaLugaresCodigosDto = new ArrayList<LugarYCodigoDTO>();
        for(LugarDeRealizacion lugar: lugaresUsuario){
            int id = lugar.getId();
            String nombre = lugar.getNombre();
            LugarYCodigoDTO unLugarCodigoDto = new LugarYCodigoDTO(nombre,id);
            listaLugaresCodigosDto.add(unLugarCodigoDto);
        }
        return listaLugaresCodigosDto;
    }
}
