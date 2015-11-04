package dtos;

import models.Deporte;
import models.Modalidad;
import models.SistemaPuntuacion;

import java.util.List;

/**
 * Created by DIego on 2/11/2015..
 */
public class DatosCrearCompetenciaDTO {

    private String competencia;
    private String deporte;
    private Modalidad modalidad;
    private SistemaPuntuacion puntuacion;
    private List<String> listaLugaresNombres;
    private String reglamento;
    private int sets;

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public boolean isTieneReglamento() {
        return tieneReglamento;
    }

    public void setTieneReglamento(boolean tieneReglamento) {
        this.tieneReglamento = tieneReglamento;
    }

    public boolean isTieneSets() {
        return tieneSets;
    }

    public void setTieneSets(boolean tieneSets) {
        this.tieneSets = tieneSets;
    }

    private boolean tieneReglamento;
    private boolean tieneSets;

    public DatosCrearCompetenciaDTO(String competencia, String deporte, Modalidad modalidad, SistemaPuntuacion puntuacion, List<String> listaLugaresNombres, String reglamento) {
        this.competencia = competencia;
        this.deporte = deporte;
        this.modalidad = modalidad;
        this.puntuacion = puntuacion;
        this.listaLugaresNombres = listaLugaresNombres;
        this.reglamento = reglamento;
    }

    public DatosCrearCompetenciaDTO(String competencia, String deporte, Modalidad modalidad, SistemaPuntuacion puntuacion, List<String> listaLugaresNombres) {
        this.competencia = competencia;
        this.deporte = deporte;
        this.modalidad = modalidad;
        this.puntuacion = puntuacion;
        this.listaLugaresNombres = listaLugaresNombres;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public SistemaPuntuacion getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(SistemaPuntuacion puntuacion) {
        this.puntuacion = puntuacion;
    }

    public List<String> getListaLugaresNombres() {
        return listaLugaresNombres;
    }

    public void setListaLugaresNombres(List<String> listaLugaresId) {
        this.listaLugaresNombres = listaLugaresId;
    }

    public String getReglamento() {
        return reglamento;
    }

    public void setReglamento(String reglamento) {
        this.reglamento = reglamento;
    }
}
