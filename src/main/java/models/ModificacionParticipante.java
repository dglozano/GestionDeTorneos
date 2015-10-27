package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class ModificacionParticipante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_modif_participante")
    private int id;
    @Column(name="nombre_participante")
    private String nombre;
    @Column(name="email")
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_modificacion")
    private Date fechaModificacion;
    /* TODO 01: Imagen participante. Ver Blob. */
}
