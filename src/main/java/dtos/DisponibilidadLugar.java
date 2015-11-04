package dtos;

/**
 * Created by DIego on 3/11/2015..
 */
public class DisponibilidadLugar{
    private String nombreLugar;
    private Integer disponibilidad;

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}