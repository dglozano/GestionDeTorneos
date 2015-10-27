package models;

import javax.persistence.*;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pais")
    private int id;
    @Column(name="nombre_pais")
    private String nombre;
}
