package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Kevincho on 21/10/2015.
 */

@Entity
public class LugarDeRealizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo_lugar")
    private Integer id;
    @Column(name = "nom_lugar")
    private String nombre;
    @Column(name = "descripcion_lugar")
    private String descripcion;
    @Column(name = "eliminado")
    private Boolean eliminado;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_elim")
    private Date fecha_elim;



}
