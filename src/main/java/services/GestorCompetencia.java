package services;

import dao.CompetenciaDao;
import dao.ParticipanteDao;
import dao.PartidoDao;
import dtos.*;
import exceptions.FixtureException.DisponibilidadesInsuficientesFixtureException;
import exceptions.FixtureException.EstadoErrorFixtureException;
import exceptions.FixtureException.PocosParticipantesFixtureException;
import exceptions.FuncionalidadEnDesarrolloException;
import models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorCompetencia {

    private CompetenciaDao competenciaDao = CompetenciaDao.getInstance();
    private ParticipanteDao participanteDao = ParticipanteDao.getInstance();
    private PartidoDao partidoDao = PartidoDao.getInstance();
    private UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();
    private GestorLugarRealizacion gestorLugarRealizacion = new GestorLugarRealizacion();
    private GestorDeporte gestorDeporte = new GestorDeporte();
    private GestorResultado gestorResultado = new GestorResultado();

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

    public List<CompetenciaDTO> listarTodasMisCompetencias(FiltrosCompetenciaDTO filtros){
        int idUsuarioLogueado = usuarioLogueado.getUsuarioLogueado().getId();
        List<Competencia> competenciasUsuario = competenciaDao.buscarTodasCompetencias(idUsuarioLogueado, filtros);
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

    public CompetenciaDTO getCompetenciaDTO(int idComp){
        Competencia comp = competenciaDao.buscarCompetenciaPorId(idComp);
        String nombre = comp.getNombre();
        String modalidad = comp.getModalidad().getModalidadString();
        String deporte = comp.getDeporte().getNombre();
        String estado = comp.getEstado().getEstadoString();
        String proximoEncuentroString;
        boolean estaEnDisputa = comp.getEstado().equals(Estado.EN_DISPUTA);
        boolean estaPlanificada = comp.getEstado().equals(Estado.PLANIFICADA);
        boolean estaFinalizada = comp.getEstado().equals(Estado.FINALIZADA);
        if(estaEnDisputa || (estaPlanificada && !estaFinalizada)){
            int fechaActual = buscarFechaActual(comp);
            Partido proximoEncuentro = getProxEncuentro(comp, fechaActual);
            proximoEncuentroString = proximoEncuentro.getLocal().getNombre() + " - " + proximoEncuentro.getVisitante().getNombre();
        }
        else {
            proximoEncuentroString = " - ";
        }
        CompetenciaDTO competencia = new CompetenciaDTO(idComp,nombre,deporte,estado,modalidad,proximoEncuentroString);
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
        competencia.setEstado(Estado.CREADA);
        competencia.setModalidad(datosCompDto.getModalidad());
        competencia.setSistemaPuntuacion(datosCompDto.getPuntuacion());
        Deporte deporte = gestorDeporte.buscarDeporte(datosCompDto.getDeporte());
        competencia.setDeporte(deporte);
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
            }
            else{
                competencia.setTantosFavorNoPresentarse(0);
            }
        }
        else{
            competencia.setAceptaEmpate(false);
            competencia.setTantosFavorNoPresentarse(0);
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
        List<Participante> listaParticipantes = participanteDao.buscarParticipantes(competencia.getId());
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
        competencia.addParticipante(nuevoParticipante);
        competencia.setEstado(Estado.CREADA);
        participanteDao.crearParticipante(nuevoParticipante);
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
        List<Participante> participantes = participanteDao.buscarParticipantes(idCompetencia);
        List<ParticipanteDTO> listaParticipantesDtos = new ArrayList<>();
        for(Participante p: participantes){
            if(!p.isEsLibre()){
                ParticipanteDTO participanteDTO = new ParticipanteDTO(p.getNombre(),p.getEmail());
                listaParticipantesDtos.add(participanteDTO);
            }
        }
        return listaParticipantesDtos;
    }

    public void cargarResultadoFinal(ResultadoFinalDTO resultadoFinalDTO){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(resultadoFinalDTO.getIdCompetencia());
        Partido partido = partidoDao.buscarPartidoPorId(resultadoFinalDTO.getIdPartido());
        if(esPrimerResultado(competencia, partido)) {
            competencia.setEstado(Estado.EN_DISPUTA);
        }
        if(esUltimoResultado(competencia, partido)) {
            competencia.setEstado(Estado.FINALIZADA);
        }
        competenciaDao.actualizarCompetencia(competencia);
        gestorResultado.cargarResultadoFinal(resultadoFinalDTO, partido);
        gestorResultado.eliminarResultadosVacios();
    }

   public void cargarResultadoPuntuacion(ResultadoPuntuacionDTO resultadoPuntuacionDTO){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(resultadoPuntuacionDTO.getIdCompetencia());
        Partido partido = partidoDao.buscarPartidoPorId(resultadoPuntuacionDTO.getIdPartido());
        if(esPrimerResultado(competencia, partido)) {
           competencia.setEstado(Estado.EN_DISPUTA);
        }
        if(esUltimoResultado(competencia, partido)) {
           competencia.setEstado(Estado.FINALIZADA);
        }
        competenciaDao.actualizarCompetencia(competencia);
        gestorResultado.cargarResultadoPuntuacion(resultadoPuntuacionDTO, partido, competencia.getTantosFavorNoPresentarse());
        gestorResultado.eliminarResultadosVacios();
    }

    public void cargarResultadoSet(ResultadoSetDTO resultadoSetDTO){
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(resultadoSetDTO.getIdCompetencia());
        Partido partido = partidoDao.buscarPartidoPorId(resultadoSetDTO.getIdPartido());
        if(esPrimerResultado(competencia, partido)) {
            competencia.setEstado(Estado.EN_DISPUTA);
        }
        if(esUltimoResultado(competencia, partido)) {
            competencia.setEstado(Estado.FINALIZADA);
        }
        competenciaDao.actualizarCompetencia(competencia);
        gestorResultado.cargarResultadoSet(resultadoSetDTO, partido, competencia.getCantidadDeSets());
        gestorResultado.eliminarResultadosVacios();
    }

    private boolean esPrimerResultado(Competencia competencia, Partido partido){
        List<Fecha> fechas = competencia.getFixture().getFechas();
        List<Partido> partidos = fechas.get(0).getPartidos();
        for (Partido p: partidos){
            if (!p.getResultados().isEmpty() && !p.isEsLibre()){
                return false;
            }
        }
        return true;
    }

    private boolean esUltimoResultado(Competencia competencia, Partido partido){
        List<Fecha> fechas = competencia.getFixture().getFechas();
        List<Partido> partidos = fechas.get(fechas.size()-1).getPartidos();
        for (Partido p: partidos){
            if (!p.isEsLibre()) {
                if (p.getId() != partido.getId()) {
                    if (p.getResultados().isEmpty()) {
                        return false;
                    }
                } else {
                    if (!p.getResultados().isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<FilaPosicionDTO> tablaPosiciones(int idCompetencia){
        ArrayList<FilaPosicionDTO> filasPosicionesDto = new ArrayList<>();
        Competencia competencia = competenciaDao.buscarCompetenciaPorId(idCompetencia);
        SistemaPuntuacion sistemaPuntuacion = competencia.getSistemaPuntuacion();
        int ptsGanado = competencia.getPuntosPartidoGanado();
        int ptsPresentarse = competencia.getPuntosPorPresentarse();
        int ptsEmpate=0;
        boolean aceptaEmpates = competencia.isAceptaEmpate();
        if(aceptaEmpates) ptsEmpate=competencia.getPuntosPartidoEmpatado();
        List<Participante> participantes = participanteDao.buscarParticipantes(idCompetencia);
        for(Participante participante: participantes){
            if(!participante.isEsLibre()){
                List<Partido> partidos = participante.getPartidosLocales();
                partidos.addAll(participante.getPartidosVisitantes());
                int puntos=0,ganados=0,empatados=0,perdidos=0,tantosFavor=0,tantosContra=0;
                FilaPosicionDTO unaFila = new FilaPosicionDTO();
                unaFila.setNombreParticipante(participante.getNombre());
                for(Partido partido: partidos){
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
        Collections.sort(filasPosicionesDto, new PosicionComparator<FilaPosicionDTO>());
        return filasPosicionesDto;
    }

    class PosicionComparator<T> implements Comparator<T> {
        @Override
        public int compare(T a, T b) {
            Integer puntosA =((Integer)((FilaPosicionDTO)a).getPuntos());
            Integer puntosB =((Integer)((FilaPosicionDTO)b).getPuntos());
            Integer diferenciaA =((Integer)((FilaPosicionDTO)a).getDiferencia());
            Integer diferenciaB =((Integer)((FilaPosicionDTO)b).getDiferencia());
            Integer favorA =((Integer)((FilaPosicionDTO)b).getFavor());
            Integer favorB =((Integer)((FilaPosicionDTO)b).getFavor());
            String nombreA =((String)((FilaPosicionDTO)b).getNombreParticipante());
            String nombreB =((String)((FilaPosicionDTO)b).getNombreParticipante());
            if(puntosA.compareTo(puntosB) != 0){
                return puntosA.compareTo(puntosB)*(-1);
            }
            if(diferenciaA.compareTo(diferenciaB) !=0){
                return diferenciaA.compareTo(diferenciaB)*(-1);
            }
            if(favorA.compareTo(favorB) != 0){
                return favorA.compareTo(favorB)*(-1);
            }
            return nombreA.compareTo(nombreB);
        }
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

    public Partido getProxEncuentro(Competencia competencia, int fechaActual) {
        boolean estaFinalizada = competencia.getEstado().equals(Estado.FINALIZADA.getEstadoString());
        if (!estaFinalizada) {
            Fecha actual = competencia.getFixture().getFechas().get(fechaActual);
            for (Partido partido : actual.getPartidos()) {
                if (partido.getResultados().isEmpty() && !partido.isEsLibre()) {
                    return partido;
                }
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

    public Partido buscarPartidoPorId(int id){
        return partidoDao.buscarPartidoPorId(id);
    }

}


