package services;

import dao.PartidoDao;
import dtos.ResultadoFinalDTO;
import models.CambioResultado;
import models.Partido;
import models.Resultado;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by DIego on 21/11/2015..
 */
public class GestorResultado {

    private PartidoDao partidoDao = PartidoDao.getInstance();

    public void cargarResultadoFinal(ResultadoFinalDTO resultadoFinalDTO, Partido partido){
        Resultado resultado;
        boolean esPrimerResultado = partido.getResultados().isEmpty();
        if(!esPrimerResultado){
            resultado = partido.getResultados().get(0);
            CambioResultado cambioResultado = new CambioResultado();
            cambioResultado.setJugoLocal(resultado.isJugoLocal());
            cambioResultado.setJugoVisitante(resultado.isJugoVisitante());
            cambioResultado.setTantosEquipoLocal(resultado.getTantosEquipoLocal());
            cambioResultado.setTantosEquipoVisitante(resultado.getTantosEquipoVisitante());
            java.sql.Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
            cambioResultado.setFechaCambio(timeNow);
            resultado.addCambioResultado(cambioResultado);
        }
        else{
            resultado = new Resultado();
        }
        resultado.setJugoLocal(resultadoFinalDTO.isSePresentoLocal());
        resultado.setJugoVisitante(resultadoFinalDTO.isSePresentoVisitante());
        if(resultadoFinalDTO.isGanoLocal()){
            resultado.setTantosEquipoLocal(1);
            resultado.setTantosEquipoVisitante(0);
        }
        else if(resultadoFinalDTO.isGanoVisitante()){
            resultado.setTantosEquipoLocal(0);
            resultado.setTantosEquipoVisitante(1);
        }
        else if (resultadoFinalDTO.isEmpate()) {
            resultado.setTantosEquipoLocal(0);
            resultado.setTantosEquipoVisitante(0);
        }
        if(esPrimerResultado)partido.addResultado(resultado);
        partidoDao.actualizarPartido(partido);
    }
}
