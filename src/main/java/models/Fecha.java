package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Fecha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_fecha")
    private int id;
    @Column (name = "numero_fecha")
    private int numeroFecha;


    @Column(name = "ronda")
    private Ronda ronda;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="id_fecha")
    private List<Partido> partidos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    public Ronda getRonda(){
        return ronda;
    }


    public Fecha() {
        super();
    }

    public Fecha(int numFecha){
        super();
        this.numeroFecha = numFecha;
    }

    public void addPartido(Partido partido){
        (this.partidos).add(partido);
    }

    public int getId() {
        return id;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public int getNumeroFecha() {
        return numeroFecha;
    }

    public void setNumeroFecha(int numeroFecha) {
        this.numeroFecha = numeroFecha;
    }

    public void setId(int id) {
        this.id = id;
    }
}
