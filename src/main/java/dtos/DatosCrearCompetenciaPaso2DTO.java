package dtos;

import models.Disponibilidad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 3/11/2015..
 */
public class DatosCrearCompetenciaPaso2DTO {

    private List<DisponibilidadLugar> lugaresDisponibilidad = new ArrayList<>();
    private int puntosPorPartidoGanado;
    private int puntosPorPresentarse;
    private boolean aceptaEmpates;
    private int puntosPorPartidoEmpatado;
    private int tantosEnCasoDeNoPresentarseOponente;
    private boolean otorgaTantosPorNoPresentarse;
    private boolean esLiga;

    public boolean isOtorgaTantosPorNoPresentarse() {
        return otorgaTantosPorNoPresentarse;
    }

    public void setOtorgaTantosPorNoPresentarse(boolean otorgaTantosPorNoPresentarse) {
        this.otorgaTantosPorNoPresentarse = otorgaTantosPorNoPresentarse;
    }

    public boolean isEsLiga() {
        return esLiga;
    }

    public void setEsLiga(boolean esLiga) {
        this.esLiga = esLiga;
    }

    public List<DisponibilidadLugar> getDisponibilidades() {
        return lugaresDisponibilidad;
    }

    public void setDisponibilidades(List<DisponibilidadLugar> lugaresDisponibilidades) {
        this.lugaresDisponibilidad = lugaresDisponibilidades;
    }

    public int getPuntosPorPartidoGanado() {
        return puntosPorPartidoGanado;
    }

    public void setPuntosPorPartidoGanado(int puntosPorPartidoGanado) {
        this.puntosPorPartidoGanado = puntosPorPartidoGanado;
    }

    public int getPuntosPorPresentarse() {
        return puntosPorPresentarse;
    }

    public void setPuntosPorPresentarse(int getPuntosPorPresentarse) {
        this.puntosPorPresentarse = getPuntosPorPresentarse;
    }

    public boolean isAceptaEmpates() {
        return aceptaEmpates;
    }

    public void setAceptaEmpates(boolean aceptaEmpates) {
        this.aceptaEmpates = aceptaEmpates;
    }

    public int getPuntosPorPartidoEmpatado() {
        return puntosPorPartidoEmpatado;
    }

    public void setPuntosPorPartidoEmpatado(int puntosPorPartidoEmpatado) {
        this.puntosPorPartidoEmpatado = puntosPorPartidoEmpatado;
    }

    public int getTantosEnCasoDeNoPresentarseOponente() {
        return tantosEnCasoDeNoPresentarseOponente;
    }

    public void setTantosEnCasoDeNoPresentarseOponente(int tantosEnCasoDeNoPresentarseOponente) {
        this.tantosEnCasoDeNoPresentarseOponente = tantosEnCasoDeNoPresentarseOponente;
    }

    public void addDisponibilidad(DisponibilidadLugar lugarDisponibilidad){
        lugaresDisponibilidad.add(lugarDisponibilidad);
    }
}
