package models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comp")
    private Integer id;
    @Column(name = "nom_comp")
    private String nombre;
    @Column(name = "reglas_comp")
    private String reglas;
    @Column(name = "pts_part_empatado")
    private Integer pts_empate;
    @Column(name = "acepta_empates")
    private boolean empate;
    @Column(name = "pts_por_presentarse")
    private Integer pts_presentarse;
    @Column(name = "tantos_a_favor_por_no_presentarse")
    private Integer tantos_favor_no_presentarse;
    @Column(name = "pts_partido_ganado")
    private Integer pts_ganar;
    @Column(name = "eliminada")
    private boolean eliminada;
    @Column(name = "fecha_elim")
    @Temporal(TemporalType.DATE)
    private Date fecha_elim;
    @Column(name = "cant_sets")
    private Integer cant_sets;
    @Column(name = "modalidad")
    private Modalidad modalidad;
    @Column(name = "Estado")
    private Estado estado;
    @Column (name = "sistemaPuntuacion")
    private SistemaPuntuacion sistemaPuntuacion;

    /*
    @OneToMany(mappedBy = "competencia")
    private List<Participante> participantes;

    @ManyToOne
    @Column (name = "deporte")
    private Deporte deporte;

    @OneToMany(mappedBy = "competencia")
    @Column(name = "disponibilidades")
    private List<Disponibilidad> disponibilidades;
    */

    @Enumerated(EnumType.STRING)
    public Modalidad getModalidad(){
        return modalidad;
    }

    @Enumerated(EnumType.STRING)
    public Estado getEstado(){
        return estado;
    }

    @Enumerated(EnumType.STRING)
    public SistemaPuntuacion getSistemaPuntuacion(){
        return sistemaPuntuacion;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

