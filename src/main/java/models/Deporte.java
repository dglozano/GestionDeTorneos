package models;

import javax.persistence.*;
import java.util.List;


@Entity
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_deporte")
    private int id;
    @Column(name = "nom_deporte")
    private String nombre;
    @ManyToMany
    @JoinTable(name= "se_practica_en",
        joinColumns = {@JoinColumn(name="id_deporte", unique=true)},
            inverseJoinColumns = {@JoinColumn(name="codigo_lugar")})
    private List<LugarDeRealizacion> lugaresRealizacion;


}
