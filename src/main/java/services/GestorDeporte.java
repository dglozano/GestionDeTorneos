package services;


import dao.DeporteDao;
import models.Deporte;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 31/10/2015..
 */
public class GestorDeporte {

    private DeporteDao deporteDao = DeporteDao.getInstance();

    public List<String> listarDeportes(){
        List<String> listaNombresDeportes = new ArrayList<String>();
        List<Deporte> listaDeportes = deporteDao.buscarDeportes();
        for(Deporte deporte: listaDeportes){
            listaNombresDeportes.add(deporte.getNombre());
        }
        return listaNombresDeportes;
    }
}
