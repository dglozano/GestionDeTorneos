package models;

import javax.persistence.*;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_localidad")
    private int id;
    @Column(name="nombre_localidad")
    private String nombre;
    @ManyToOne
    @JoinColumn (name="id_provincia")
    private Provincia provincia;
}
