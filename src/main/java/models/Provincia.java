package models;

import javax.persistence.*;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_provincia")
    private int id;
    @Column(name="nombre_provincia")
    private String nombre;
    @ManyToOne
    @JoinColumn (name="id_pais")
    private Pais pais;
}
