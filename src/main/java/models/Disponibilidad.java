package models;

import javax.persistence.*;

/**
 * Created by Kevincho on 21/10/2015.
 */

@Entity
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "disponibilidad")
    private Integer disponibilidad;
    @ManyToOne
    @Column(name = "lugarDeRealizacion")
    private LugarDeRealizacion lugarDeRealizacion;
}
