package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class CambioResultado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cambio_resultado")
    private int id;
    @Column(name="jugo_local")
    private boolean jugoLocal;
    @Column(name="jugo_visitante")
    private boolean jugoVisitante;
    @Column(name="equipo_local")
    private int tantosEquipoLocal;
    @Column(name="equipo_visitante")
    private int tantosEquipoVisitante;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_cambio")
    private Date fechaCambio;
    @Column(name="gano_local_desempate")
    private boolean ganoLocalDesempate;

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGanoLocalDesempate() {
        return ganoLocalDesempate;
    }

    public void setGanoLocalDesempate(boolean ganoLocalDesempate) {
        this.ganoLocalDesempate = ganoLocalDesempate;
    }

    public CambioResultado() {
    }

    public int getId() {
        return id;
    }

    public boolean isJugoLocal() {
        return jugoLocal;
    }

    public boolean isJugoVisitante() {
        return jugoVisitante;
    }

    public int getTantosEquipoLocal() {
        return tantosEquipoLocal;
    }

    public int getTantosEquipoVisitante() {
        return tantosEquipoVisitante;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setJugoLocal(boolean jugoLocal) {
        this.jugoLocal = jugoLocal;
    }

    public void setJugoVisitante(boolean jugoVisitante) {
        this.jugoVisitante = jugoVisitante;
    }

    public void setTantosEquipoLocal(int tantosEquipoLocal) {
        this.tantosEquipoLocal = tantosEquipoLocal;
    }

    public void setTantosEquipoVisitante(int tantosEquipoVisitante) {
        this.tantosEquipoVisitante = tantosEquipoVisitante;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
