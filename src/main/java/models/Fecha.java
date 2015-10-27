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
    @OneToMany
    @JoinColumn(name="id_fecha")
    private List<Partido> partidos;

    @Enumerated(EnumType.STRING)
    public Ronda getRonda(){
        return ronda;
    }
}
