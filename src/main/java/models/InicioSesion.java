package models;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class InicioSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_inicio_sesion")
    private int id;
    @Column(name = "ip")
    private String ip;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_elim")
    private Date fechaEliminacion;

}
