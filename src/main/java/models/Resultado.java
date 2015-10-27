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
    @OneToMany
    @JoinColumn(name="id_resultado")
    private List<CambioResultado> cambiosResultado;

}
