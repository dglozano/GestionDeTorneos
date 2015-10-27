package models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
public class LugarDeRealizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo_lugar")
    private int id;
    @Column(name = "nom_lugar")
    private String nombre;
    @Column(name = "descripcion_lugar")
    private String descripcion;
    @Column(name = "eliminado")
    private boolean eliminado;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_elim")
    private Date fecha_elim;
    @ManyToMany(mappedBy="lugaresRealizacion")
    private List<Deporte> deportes;
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;



}
