package models;

import javax.persistence.*;
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
    @Column(name = "ronda")
    private Ronda ronda;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="id_fecha")
    private List<Partido> partidos;

    @Enumerated(EnumType.STRING)
    public Ronda getRonda(){
        return ronda;
    }


    public Fecha() {
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
}
