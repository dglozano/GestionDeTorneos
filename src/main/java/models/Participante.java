package models;

import javax.persistence.*;
import java.util.List;


@Entity
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_participante")
    private int id;
    @Column(name = "nom_partic")
    private String nombre;
    @Column(name = "puntos")
    private int puntos;
    @Column(name = "email_partic")
    private String email;
    @Column(name ="es_libre")
    private boolean esLibre;

    @OneToMany
    @JoinColumn(name = "id_participante")
    private List<ModificacionParticipante> modificacionesParticipante;
    /* TODO 00: Ver relacion partido-participante. */
    /*@OneToMany(mappedBy = "local")
    @Column(name="partidos")
    private List<Partido> partidosDeLocal;
    @OneToMany(mappedBy = "visitante")
    @Column(name="partidos")
    private List<Partido> partidosDeVisitante;
    @OneToMany(mappedBy = "ganador")
    @Column(name="partidos")
    private List<Partido> partidosGanador;*/
    /* TODO 01: Imagen participante. Ver Blob. */


}
