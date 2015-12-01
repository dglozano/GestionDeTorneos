package services;
import dao.CompetenciaDao;
import dao.ParticipanteDao;
import dao.PartidoDao;
import dtos.*;
import exceptions.FixtureException.DisponibilidadesInsuficientesFixtureException;
import exceptions.FixtureException.EstadoErrorFixtureException;
import exceptions.FuncionalidadEnDesarrolloException;
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
    private PartidoDao partidoDao = PartidoDao.getInstance();
    private GestorResultado gestorResultado = new GestorResultado();

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
            EstadoErrorFixtureException,DisponibilidadesInsuficientesFixtureException,FuncionalidadEnDesarrolloException{
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
                    throw new FuncionalidadEnDesarrolloException();
                case ELIM_SIMPLE:
                    throw new FuncionalidadEnDesarrolloException();
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
            listaParticipantes.add(listaParticipantes.remove(1));
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

    public void cargarResultadoFinal(ResultadoFinalDTO resultadoFinalDTO){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(resultadoFinalDTO.getIdCompetencia());
        Partido partido = partidoDao.buscarPartidoPorId(resultadoFinalDTO.getIdPartido());
        gestorResultado.cargarResultadoFinal(resultadoFinalDTO,partido);
        if(!competencia.getEstado().equals(Estado.EN_DISPUTA)) competencia.setEstado(Estado.EN_DISPUTA);
        competenciaDao.actualizarCompetencia(competencia);
        gestorResultado.eliminarResultadosVacios();
        //TODO 00: VER SI ES PRIMER O ULTIMO PARTIDO
    }

   public void cargarResultadoPuntuacion(ResultadoPuntuacionDTO resultadoPuntuacionDTO){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(resultadoPuntuacionDTO.getIdCompetencia());
        Partido partido = partidoDao.buscarPartidoPorId(resultadoPuntuacionDTO.getIdPartido());
       if(!competencia.getEstado().equals(Estado.EN_DISPUTA)){
           competencia.setEstado(Estado.EN_DISPUTA);
           competenciaDao.actualizarCompetencia(competencia);
       }
        gestorResultado.cargarResultadoPuntuacion(resultadoPuntuacionDTO, partido, competencia.getTantosFavorNoPresentarse());
        gestorResultado.eliminarResultadosVacios();
        //TODO 00: VER SI ES PRIMER O ULTIMO PARTIDO
    }

    public ArrayList<FilaPosicionDTO> tablaPosiciones(int idCompetencia){
        ArrayList<FilaPosicionDTO> filasPosicionesDto = new ArrayList<>();
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        Modalidad modalidad = competencia.getModalidad();
        SistemaPuntuacion sistemaPuntuacion = competencia.getSistemaPuntuacion();
        int ptsGanado = competencia.getPuntosPartidoGanado();
        int ptsPresentarse = competencia.getPuntosPorPresentarse();
        int ptsEmpate=0,tantosFavorNoPresentarse=0;
        boolean aceptaEmpates = competencia.isAceptaEmpate();
        boolean otorgaTantos = competencia.isOtorgaTantosNoPresentarse();
        if(aceptaEmpates) ptsEmpate=competencia.getPuntosPartidoEmpatado();
        if(otorgaTantos) tantosFavorNoPresentarse = competencia.getTantosFavorNoPresentarse();
        List<Participante> participantes = competencia.getParticipantes();
        for(Participante participante: participantes){
            if(!participante.isEsLibre()){
                List<Partido> partidos = participante.getPartidosLocales();
                partidos.addAll(participante.getPartidosVisitantes());
                int puntos=0,ganados=0,empatados=0,perdidos=0,tantosFavor=0,tantosContra=0;
                FilaPosicionDTO unaFila = new FilaPosicionDTO();
                unaFila.setNombreParticipante(participante.getNombre());
                for(Partido partido: partidos){
                    //TODO 01: sets
                    if(!partido.getResultados().isEmpty()){
                        Resultado resultado = partido.getResultados().get(0);
                        boolean esGanador= (partido.getGanador() != null) ? partido.getGanador().equals(participante) : false;
                        boolean esLocal = partido.getLocal().equals(participante);
                        boolean sePresento = ((esLocal && resultado.isJugoLocal()) || (!esLocal && resultado.isJugoVisitante()));
                        if(sePresento){
                            puntos+=ptsPresentarse;
                            if(esGanador){
                                puntos+=ptsGanado;
                                ganados++;
                            }
                            else if (aceptaEmpates && (resultado.getTantosEquipoLocal() == resultado.getTantosEquipoVisitante())){
                                puntos+=ptsEmpate;
                                empatados++;
                            }
                            else{
                                perdidos++;
                            }
                        }
                        else{
                            perdidos++;
                        }
                        if(sistemaPuntuacion.equals(SistemaPuntuacion.PUNTUACION)){
                            if(esLocal){
                                tantosFavor+=resultado.getTantosEquipoLocal();
                                tantosContra+=resultado.getTantosEquipoVisitante();
                            }
                            else{
                                tantosFavor+=resultado.getTantosEquipoVisitante();
                                tantosContra+=resultado.getTantosEquipoLocal();
                            }
                        }
                    }
                }
                unaFila.setPuntos(puntos);
                unaFila.setGanados(ganados);
                unaFila.setPerdidos(perdidos);
                unaFila.setEmpatados(empatados);
                unaFila.setFavor(tantosFavor);
                unaFila.setContra(tantosContra);
                unaFila.setDiferencia(tantosFavor-tantosContra);
                unaFila.setJugados(ganados+perdidos+empatados);
                filasPosicionesDto.add(unaFila);
            }
        }
        return filasPosicionesDto;
    }
    public void actualizarCompetencia(Competencia competencia){
        competenciaDao.actualizarCompetencia(competencia);
    }
    public Partido buscarPartidoPorId(int id){
        return partidoDao.buscarPartidoPorId(id);
    }

    public int buscarFechaActual(Competencia competencia){
        int nroFecha = 0;
        for (Fecha fecha: competencia.getFixture().getFechas()){
            for (Partido partido: fecha.getPartidos()){
                if (partido.getResultados().isEmpty() && !partido.isEsLibre()){
                    return nroFecha;
                }
            }
            nroFecha++;
        }
        return nroFecha;
    }

    public Partido getProxEncuentro(Competencia competencia, int fechaActual){
        Fecha actual = competencia.getFixture().getFechas().get(fechaActual);
        for (Partido partido: actual.getPartidos()){
            if (partido.getResultados().isEmpty() && !partido.isEsLibre()){
                return partido;
            }
        }
        return null;
    }

    public int buscarFechaPartido(Competencia competencia, int idPartido){
        int nroFecha = 0;
        for (Fecha fecha: competencia.getFixture().getFechas()){
            for (Partido partido: fecha.getPartidos()){
                if (partido.getId() == idPartido){
                    return nroFecha;
                }
            }
            nroFecha++;
        }
        nroFecha = -1;
        return nroFecha;
    }

    public boolean partidoHabilitado(Competencia competencia, int idPartido){
        int nroFechaPartido = buscarFechaPartido(competencia, idPartido);
        if (nroFechaPartido==-1)
            return false;
        int nroFechaActual = buscarFechaActual(competencia);
        if (nroFechaPartido>=0 && nroFechaPartido<=nroFechaActual){
            return true;
        }
        return false;
    }
}


