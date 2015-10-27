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
}
