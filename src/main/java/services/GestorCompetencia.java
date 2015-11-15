package services;
import dao.CompetenciaDao;
import dao.ParticipanteDao;
import dtos.*;
import exceptions.FixtureException.DisponibilidadesInsuficientesFixtureException;
import exceptions.FixtureException.EstadoErrorFixtureException;
import exceptions.FixtureException.PocosParticipantesFixtureException;
import models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorCompetencia {
    private CompetenciaDao competenciaDao = CompetenciaDao.getInstance();
    private UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();
    private GestorLugarRealizacion gestorLugarRealizacion = new GestorLugarRealizacion();
    private GestorDeporte gestorDeporte = new GestorDeporte();
    private ParticipanteDao participanteDao = ParticipanteDao.getInstance();

    public void nuevaCompetencia(Competencia c) {
        competenciaDao.crearCompetencia(c);
    }

    public Competencia buscarCompetenciaPorId(int idCompetencia){
        return competenciaDao.buscarCompetenciaPorId(idCompetencia);
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

    public CompetenciaDTO getCompetencia(int idComp){
        Competencia comp = competenciaDao.buscarCompetenciaPorId(idComp);
        String nombre = comp.getNombre();
        String modalidad = comp.getModalidad().getModalidadString();
        String deporte = comp.getDeporte().getNombre();
        String estado = comp.getEstado().getEstadoString();
        CompetenciaDTO competencia = new CompetenciaDTO(idComp,nombre,deporte,estado,modalidad);
        /*TODO 01: Agregar proximos encuentros cuando el fixture este funcionando*/
        return competencia;
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

    public void crearCompetencia(DatosCrearCompetenciaDTO datosCompDto, DatosCrearCompetenciaPaso2DTO datosCompDtoPaso2){
        Competencia competencia = new Competencia();
        competencia.setNombre(datosCompDto.getCompetencia().toUpperCase());
        competencia.setUsuario(usuarioLogueado.getUsuarioLogueado());
        Deporte deporte = gestorDeporte.buscarDeporte(datosCompDto.getDeporte());
        competencia.setDeporte(deporte);
        competencia.setEstado(Estado.CREADA);
        competencia.setModalidad(datosCompDto.getModalidad());
        competencia.setSistemaPuntuacion(datosCompDto.getPuntuacion());
        if(datosCompDto.getPuntuacion().equals(SistemaPuntuacion.SET))
            competencia.setCantidadDeSets(datosCompDto.getSets());
        if(datosCompDto.isTieneReglamento())
            competencia.setReglas(datosCompDto.getReglamento());
        cargarDisponibilidades(datosCompDtoPaso2,competencia);
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

    private void cargarDisponibilidades(DatosCrearCompetenciaPaso2DTO datosCompPaso2,Competencia competencia) {
        for(DisponibilidadLugar dispLug: datosCompPaso2.getDisponibilidades()){
            String nombreLugar = dispLug.getNombreLugar().toUpperCase();
            int disponiblidadInt = dispLug.getDisponibilidad();
            LugarDeRealizacion lugar = gestorLugarRealizacion.buscarLugarPorNombre(nombreLugar);
            Disponibilidad unaDisponibilidad = new Disponibilidad();
            unaDisponibilidad.setDisponibilidad(disponiblidadInt);
            unaDisponibilidad.setLugarDeRealizacion(lugar);
            competencia.addDisponibilidad(unaDisponibilidad);
        }
    }

    public void generarFixture(int idCompetencia) throws PocosParticipantesFixtureException,
            EstadoErrorFixtureException,DisponibilidadesInsuficientesFixtureException{
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        boolean estaEnDisputa = competencia.getEstado().equals(Estado.EN_DISPUTA);
        boolean estaFinalizada = competencia.getEstado().equals(Estado.FINALIZADA);
        if(estaEnDisputa || estaFinalizada){
            throw new EstadoErrorFixtureException("La competencia esta en Disputa o Finalizada");
        }
        else {
            switch (competencia.getModalidad()) {
                case LIGA:
                    generarFixtureLiga(competencia);
                    break;
                case ELIM_DOBLE:
                    //NO IMPLEMENTADO
                    System.out.println("Funcionalidad en desarrollo");
                    break;
                case ELIM_SIMPLE:
                    //NO IMPLEMENTADO
                    System.out.println("Funcionalidad en desarrollo");
                    break;
            }
        }
    }

    private void generarFixtureLiga(Competencia competencia)
            throws PocosParticipantesFixtureException,DisponibilidadesInsuficientesFixtureException{
        if(competencia.getFixture()!=null){
            eliminarFixture(competencia);
        }
        List<Participante> listaParticipantes = competencia.getParticipantes();
        List<Disponibilidad> listaDisponibilidades = competencia.getDisponibilidades();
        int totalParticipantes = listaParticipantes.size();
        if(totalParticipantes < 2){
            throw new PocosParticipantesFixtureException("La competencia debe tener por lo menos dos participantes");
        }
        if(totalParticipantes % 2 == 1){
            totalParticipantes++;
            Participante participanteLibre = new Participante(Participante.LIBRE);
            listaParticipantes.add(participanteLibre);
            participanteDao.crearParticipante(participanteLibre);
        }
        Collections.shuffle(listaParticipantes);
        Fixture fixture = new Fixture();
        int mitad = totalParticipantes / 2 ;
        if(calcularTotalDisponibilidades(listaDisponibilidades) < mitad){
            throw new DisponibilidadesInsuficientesFixtureException("La competencia no tiene suficientes Disponibilidades asignadas");
        }
        for(int i=0; i<totalParticipantes-1;i++){
            Fecha fecha = new Fecha(i+1);
            int disponibilidad = listaDisponibilidades.get(0).getDisponibilidad();
            for(int j=0; j<mitad;j++){
                Partido partido = new Partido();
                Participante participanteLocal  = listaParticipantes.get(j);
                Participante participanteVisitante = listaParticipantes.get(totalParticipantes-j-1);
                partido.setLocal(participanteLocal);
                partido.setVisitante(participanteVisitante);
                participanteLocal.addPartidoLocal(partido);
                participanteVisitante.addPartidoVisitante(partido);
                if(participanteLocal.isEsLibre() || participanteVisitante.isEsLibre()){
                    partido.setEsLibre(true);
                }
                if(disponibilidad ==0){
                    listaDisponibilidades.add(listaDisponibilidades.remove(0));
                    disponibilidad = listaDisponibilidades.get(0).getDisponibilidad();
                }
                partido.setLugar(listaDisponibilidades.get(0).getLugarDeRealizacion());
                disponibilidad--;
                fecha.addPartido(partido);
            }
            listaDisponibilidades.add(listaDisponibilidades.remove(0));
            fixture.addFecha(fecha);
            listaParticipantes.add(0, listaParticipantes.remove(totalParticipantes - 1));
        }
        competencia.setEstado(Estado.PLANIFICADA);
        competencia.setFixture(fixture);
        competenciaDao.actualizarCompetencia(competencia);
        participanteDao.eliminarParticipantesSinCompetencia();
    }

    private int calcularTotalDisponibilidades(List<Disponibilidad> listaDisponibilidades) {
        int total=0;
        for(Disponibilidad disp: listaDisponibilidades){
            total+=disp.getDisponibilidad();
        }
        return total;
    }

    public boolean existeNombreParticipante(int idCompetencia, String nombreParticipante){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        for(Participante p: competencia.getParticipantes()){
            if(!p.isEsLibre() && p.getNombre().toUpperCase().equals(nombreParticipante.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    public boolean existeEmailParticipante(int idCompetencia, String emailParticipante){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        for(Participante p: competencia.getParticipantes()){
            if(!p.isEsLibre() && p.getEmail().equals(emailParticipante)){
                return true;
            }
        }
        return false;
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

    public void agregarParticipante(ParticipanteDTO participanteDTO,int idCompetencia){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        Participante nuevoParticipante = new Participante();
        nuevoParticipante.setNombre(participanteDTO.getNombreParticipante());
        nuevoParticipante.setEmail(participanteDTO.getEmailParticipante());
        if (participanteDTO.isTieneImagen()){
            System.out.println("ACA ESTA 2");
            nuevoParticipante.setImagen(participanteDTO.getImagenParticipante());
        }
        nuevoParticipante.setEsLibre(false);
        if(competencia.getFixture()!=null){
            eliminarFixture(competencia);
        }
        participanteDao.crearParticipante(nuevoParticipante);
        competencia.addParticipante(nuevoParticipante);
        competencia.setEstado(Estado.CREADA);
        competenciaDao.actualizarCompetencia(competencia);
        participanteDao.eliminarParticipantesSinCompetencia();
    }

    public void eliminarFixture(Competencia competencia){
        Fixture fixtureBorrar = competencia.getFixture();
        competencia.setFixture(null);
        for(Participante part: competencia.getParticipantes()){
            if(part.isEsLibre()){
                competencia.getParticipantes().remove(part);
                break;
            }
        }
        competenciaDao.actualizarCompetencia(competencia);
        competenciaDao.eliminarFixture(fixtureBorrar);
    }

    public List<ParticipanteDTO> listarParticipantesDtos(int idCompetencia){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        List<ParticipanteDTO> listaParticipantesDtos = new ArrayList<>();
        for(Participante p: competencia.getParticipantes()){
            if(!p.isEsLibre()){
                ParticipanteDTO participanteDTO = new ParticipanteDTO(p.getNombre(),p.getEmail());
                participanteDTO.setTieneImagen(false);
                listaParticipantesDtos.add(participanteDTO);
            }
        }
        return listaParticipantesDtos;
    }
}


