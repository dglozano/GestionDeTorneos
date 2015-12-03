package services;


import dao.DeporteDao;
import models.Deporte;

import java.util.ArrayList;
import java.util.List;

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

    public Deporte buscarDeporte(String deporte){
        return deporteDao.buscarDeporte(deporte);
    }
}
