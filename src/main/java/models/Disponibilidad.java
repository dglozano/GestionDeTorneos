package models;

import javax.persistence.*;


@Entity
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_disponibilidad")
    private int id;
    @Column(name = "disponibilidad")
    private int disponibilidad;
    @ManyToOne
    @JoinColumn(name="codigo_lugar")
    private LugarDeRealizacion lugarDeRealizacion;
}
