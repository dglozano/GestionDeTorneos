package dtos;

import models.Disponibilidad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 3/11/2015..
 */
public class DatosCrearCompetenciaPaso2DTO {

    private List<Disponibilidad> disponibilidades = new ArrayList<>();
    private int puntosPorPartidoGanado;
    private int getPuntosPorPresentarse;
    private boolean aceptaEmpates;
    private int puntosPorPartidoEmpatado;
    private int tantosEnCasoDeNoPresentarseOponente;

    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public int getPuntosPorPartidoGanado() {
        return puntosPorPartidoGanado;
    }

    public void setPuntosPorPartidoGanado(int puntosPorPartidoGanado) {
        this.puntosPorPartidoGanado = puntosPorPartidoGanado;
    }

    public int getGetPuntosPorPresentarse() {
        return getPuntosPorPresentarse;
    }

    public void setGetPuntosPorPresentarse(int getPuntosPorPresentarse) {
        this.getPuntosPorPresentarse = getPuntosPorPresentarse;
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

    public void addDisponibilidad(Disponibilidad disponibilidad){
        disponibilidades.add(disponibilidad);
    }
}
