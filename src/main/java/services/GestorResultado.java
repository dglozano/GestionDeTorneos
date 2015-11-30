package services;

import dao.PartidoDao;
import dtos.ResultadoFinalDTO;
import dtos.ResultadoPuntuacionDTO;
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
        Resultado resultado = new Resultado();
        boolean esPrimerResultado = partido.getResultados().isEmpty();
        if(!esPrimerResultado){
            Resultado resultadoViejo = partido.getResultados().get(0);
            resultado.setCambiosResultado(resultadoViejo.getCambiosResultado());
            agregarCambioResultado(resultado, resultadoViejo);
            partido.getResultados().clear();
        }
        resultado.setJugoLocal(resultadoFinalDTO.isSePresentoLocal());
        resultado.setJugoVisitante(resultadoFinalDTO.isSePresentoVisitante());
        if(resultadoFinalDTO.isGanoLocal()){
            resultado.setTantosEquipoLocal(1);
            resultado.setTantosEquipoVisitante(0);
            partido.setGanador(partido.getLocal());
        }
        else if(resultadoFinalDTO.isGanoVisitante()){
            resultado.setTantosEquipoLocal(0);
            resultado.setTantosEquipoVisitante(1);
            partido.setGanador(partido.getVisitante());
        }
        else if (resultadoFinalDTO.isEmpate()) {
            resultado.setTantosEquipoLocal(0);
            resultado.setTantosEquipoVisitante(0);
        }
        partido.addResultado(resultado);
        partidoDao.actualizarPartido(partido);
    }

   public void cargarResultadoPuntuacion(ResultadoPuntuacionDTO resultadoPuntuacionDTO,Partido partido,int tantosNoPresentarse){
        Resultado resultado = new Resultado();
        boolean esPrimerResultado = partido.getResultados().isEmpty();
        if(!esPrimerResultado){
            Resultado resultadoViejo = partido.getResultados().get(0);
            resultado.setCambiosResultado(resultadoViejo.getCambiosResultado());
            agregarCambioResultado(resultado, resultadoViejo);
            partido.getResultados().clear();
        }
        if(resultadoPuntuacionDTO.isSePresentoLocal() && resultadoPuntuacionDTO.isSePresentoVisitante()){
            int tantosLocal = resultadoPuntuacionDTO.getTantosLocal();
            int tantosVisitante = resultadoPuntuacionDTO.getTantosVisitante();
            cargarResultado(resultado,true,true,tantosLocal,tantosVisitante);
            if(tantosLocal > tantosVisitante){
                partido.setGanador(partido.getLocal());
            }
            else if(tantosVisitante > tantosLocal){
                partido.setGanador(partido.getVisitante());
            }
            if(resultadoPuntuacionDTO.isTieneDesempate()){
                if(resultadoPuntuacionDTO.isGanoLocalDesempate()){
                    partido.setGanador(partido.getLocal());
                    resultado.setGanoLocalDesempate(true);
                }
                else{
                    partido.setGanador(partido.getVisitante());
                    resultado.setGanoLocalDesempate(false);
                }
            }
        }
        else{
            if(resultadoPuntuacionDTO.isSePresentoLocal()){
                partido.setGanador(partido.getLocal());
                cargarResultado(resultado, true, false, tantosNoPresentarse, 0);
            }
            else{
                partido.setGanador(partido.getVisitante());
                cargarResultado(resultado,false,true,0,tantosNoPresentarse);
            }
        }
        partido.addResultado(resultado);
        partidoDao.actualizarPartido(partido);
    }

    private void cargarResultado(Resultado resultado,boolean sePresentoLocal, boolean sePresentoVisitante,
                                 int tantosLocal, int tantosVisitante) {
        resultado.setJugoLocal(sePresentoLocal);
        resultado.setJugoVisitante(sePresentoVisitante);
        resultado.setTantosEquipoLocal(tantosLocal);
        resultado.setTantosEquipoVisitante(tantosVisitante);
    }

    private void agregarCambioResultado(Resultado nuevoResultado,Resultado resultado) {
        CambioResultado cambioResultado = new CambioResultado();
        cambioResultado.setJugoLocal(resultado.isJugoLocal());
        cambioResultado.setJugoVisitante(resultado.isJugoVisitante());
        cambioResultado.setTantosEquipoLocal(resultado.getTantosEquipoLocal());
        cambioResultado.setTantosEquipoVisitante(resultado.getTantosEquipoVisitante());
        cambioResultado.setGanoLocalDesempate(resultado.isGanoLocalDesempate());
        Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());
        cambioResultado.setFechaCambio(timeNow);
        nuevoResultado.addCambioResultado(cambioResultado);
    }

    public void eliminarResultadosVacios(){
        partidoDao.eliminarResultadosVacios();
    }
}
