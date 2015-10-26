package models;

import javax.persistence.*;


@Entity
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_disponibilidad")
    private Integer id;
    @Column(name = "disponibilidad")
    private Integer disponibilidad;
    @ManyToOne
    //@Column(name = "lugarDeRealizacion")  -> ManyToOne NO PERMITE COLUMN
    private LugarDeRealizacion lugarDeRealizacion;
}
