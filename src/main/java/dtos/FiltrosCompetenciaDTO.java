package dtos;

import models.Estado;
import models.Modalidad;

/**
 * Created by DIego on 1/11/2015..
 */
public class FiltrosCompetenciaDTO {
    private String nombre;
    private Estado estado;
    private Modalidad modalidad;
    private String deporte;

    private boolean filtroNombreActivo;
    private boolean filtroEstadoActivo;
    private boolean filtroModalidadActivo;
    private boolean filtroDeporteActivo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public boolean isFiltroNombreActivo() {
        return filtroNombreActivo;
    }

    public void setFiltroNombreActivo(boolean filtroNombreActivo) {
        this.filtroNombreActivo = filtroNombreActivo;
    }

    public boolean isFiltroEstadoActivo() {
        return filtroEstadoActivo;
    }

    public void setFiltroEstadoActivo(boolean filtroEstadoActivo) {
        this.filtroEstadoActivo = filtroEstadoActivo;
    }

    public boolean isFiltroModalidadActivo() {
        return filtroModalidadActivo;
    }

    public void setFiltroModalidadActivo(boolean filtroModalidadActivo) {
        this.filtroModalidadActivo = filtroModalidadActivo;
    }

    public boolean isFiltroDeporteActivo() {
        return filtroDeporteActivo;
    }

    public void setFiltroDeporteActivo(boolean filtroDeporteActivo) {
        this.filtroDeporteActivo = filtroDeporteActivo;
    }
}
