package services;
import dao.CompetenciaDao;
import dtos.DatosCrearCompetenciaDTO;
import dtos.DatosCrearCompetenciaPaso2DTO;
import dtos.FiltrosCompetenciaDTO;
import models.*;
import dtos.CompetenciaDTO;

import java.util.ArrayList;
import java.util.List;

public class GestorCompetencia {
    private CompetenciaDao competenciaDao = CompetenciaDao.getInstance();
    private UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();

    public void nuevaCompetencia(Competencia c) {
        competenciaDao.crearCompetencia(c);
    }

    public List<CompetenciaDTO> listarTodasMisCompetencias(){
        int idUsuarioLogueado = usuarioLogueado.getUsuarioLogueado().getId();
        List<Competencia> competenciasUsuario = competenciaDao.buscarTodasCompetencias(idUsuarioLogueado);
        List<CompetenciaDTO> listaCompetenciasDTO= new ArrayList<CompetenciaDTO>();
        for(Competencia comp: competenciasUsuario){
            int id = comp.getId();
            String nombre = comp.getNombre();
            String estado = comp.getEstado().getEstadoString();
            String modalidad = comp.getModalidad().getModalidadString();
            String deporte = comp.getDeporte().getNombre();
            CompetenciaDTO unaCompetencia = new CompetenciaDTO(id,nombre,deporte,estado,modalidad);
            listaCompetenciasDTO.add(unaCompetencia);
        }
        return listaCompetenciasDTO;
    }

    public List<CompetenciaDTO> filtrarMisCompetencias(FiltrosCompetenciaDTO filtros){
        int idUsuarioLogueado = usuarioLogueado.getUsuarioLogueado().getId();
        List<Competencia> competenciasUsuario = competenciaDao.filtrarCompetencias(idUsuarioLogueado, filtros);
        List<CompetenciaDTO> listaCompetenciasDTO= new ArrayList<CompetenciaDTO>();
        for(Competencia comp: competenciasUsuario){
            int id = comp.getId();
            String nombre = comp.getNombre();
            String estado = comp.getEstado().getEstadoString();
            String modalidad = comp.getModalidad().getModalidadString();
            String deporte = comp.getDeporte().getNombre();
            CompetenciaDTO unaCompetencia = new CompetenciaDTO(id,nombre,deporte,estado,modalidad);
            listaCompetenciasDTO.add(unaCompetencia);
        }
        return listaCompetenciasDTO;
    }

    public boolean existeNombre(String nombre){
        boolean existe = false;
        List<Competencia> listaCompetencias = competenciaDao.buscarTodasCompetencias();
        for(Competencia comp: listaCompetencias){
            if(comp.getNombre().equals(nombre)){
                existe = true;
                break;
            }
        }
        return existe;
    }
    //TODO 05: metodo crear competencia
    public void crearCompetencia(DatosCrearCompetenciaDTO datosCompDto, DatosCrearCompetenciaPaso2DTO datosCompDtoPaso2){
        /*for(Disponibilidad disp: datosCompDtoPaso2.getDisponibilidades()){
            System.out.println("Lugar: "+disp.getLugarDeRealizacion().getNombre()+ " disp "+disp.getDisponibilidad());
        }*/
        Competencia competencia = new Competencia();
        competencia.setNombre(datosCompDto.getCompetencia().toUpperCase());
        competencia.setUsuario(usuarioLogueado.getUsuarioLogueado());
        competencia.setDeporte(datosCompDto.getDeporte());
        competencia.setEstado(Estado.CREADA);
        competencia.setModalidad(datosCompDto.getModalidad());
        competencia.setSistemaPuntuacion(datosCompDto.getPuntuacion());
        if(datosCompDto.getPuntuacion().equals(SistemaPuntuacion.SET))
            competencia.setCantidadDeSets(datosCompDto.getSets());
        if(datosCompDto.isTieneReglamento())
            competencia.setReglas(datosCompDto.getReglamento());
        for(Disponibilidad disponibilidad: datosCompDtoPaso2.getDisponibilidades()){
            competencia.addDisponibilidad(disponibilidad);
        }
        if(datosCompDtoPaso2.isEsLiga()){
            competencia.setPuntosPartidoGanado(datosCompDtoPaso2.getPuntosPorPartidoGanado());
            competencia.setPuntosPorPresentarse(datosCompDtoPaso2.getPuntosPorPresentarse());
            if(datosCompDtoPaso2.isAceptaEmpates()){
                competencia.setAceptaEmpate(true);
                competencia.setPuntosPartidoEmpatado(datosCompDtoPaso2.getPuntosPorPartidoEmpatado());
            }
            else{
                competencia.setAceptaEmpate(false);
            }
            if(datosCompDtoPaso2.isOtorgaTantosPorNoPresentarse()){
                competencia.setTantosFavorNoPresentarse(datosCompDtoPaso2.getTantosEnCasoDeNoPresentarseOponente());
                competencia.setOtorgaTantosNoPresentarse(true);
            }
            else{
                competencia.setOtorgaTantosNoPresentarse(false);
            }
        }
        else{
            competencia.setAceptaEmpate(false);
            competencia.setOtorgaTantosNoPresentarse(false);
        }
        competenciaDao.crearCompetencia(competencia);
    }

    public Modalidad asociarModalidad(String modalidadString) {
        switch(modalidadString){
            case "Liga" : return Modalidad.LIGA;
            case "Eliminatoria Simple" : return Modalidad.ELIM_SIMPLE;
            case "Eliminatoria Doble" : return Modalidad.ELIM_DOBLE;
        }
        return null;
    }

    public Estado asociarEstado(String estadoString) {
        switch(estadoString){
            case "Creada": return Estado.CREADA;
            case "En disputa": return Estado.EN_DISPUTA;
            case "Eliminada": return Estado.ELIMINADA;
            case "Planificada": return Estado.PLANIFICADA;
            case "Finalizada": return Estado.FINALIZADA;
        }
        return null;
    }

    public SistemaPuntuacion asociarSistemaPuntuacion(String puntuacionString) {
        switch(puntuacionString){
            case "Puntos": return SistemaPuntuacion.PUNTUACION;
            case "Resultado Final": return SistemaPuntuacion.RESULTADO_FINAL;
            case "Sets": return SistemaPuntuacion.SET;
        }
        return null;
    }
}
