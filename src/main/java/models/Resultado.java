package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_resultado")
    private int id;
    @Column(name="jugo_local")
    private boolean jugoLocal;
    @Column(name="jugo_visitante")
    private boolean jugoVisitante;
    @Column(name="equipo_local")
    private int tantosEquipoLocal;
    @Column(name="equipo_visitante")
    private int tantosEquipoVisitante;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_resultado")
    private List<CambioResultado> cambiosResultado;


    public Resultado() {
    }

    public void addCambioResultado(CambioResultado cambio){
        (this.cambiosResultado).add(cambio);
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

    public List<CambioResultado> getCambiosResultado() {
        return cambiosResultado;
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

    public void setCambiosResultado(List<CambioResultado> cambiosResultado) {
        this.cambiosResultado = cambiosResultado;
    }

    public void setId(int id) {
        this.id = id;
    }
}
