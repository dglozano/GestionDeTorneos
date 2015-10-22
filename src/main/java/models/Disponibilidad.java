package models;

import javax.persistence.*;

/**
 * Created by Kevincho on 21/10/2015.
 */

@Entity
public class Disponibilidad {

    @Column(name = "disponibilidad")
    private Integer disponibilidad;
    @ManyToOne
    @Column(name = "lugarDeRealizacion")
    private LugarDeRealizacion lugarDeRealizacion;
}
