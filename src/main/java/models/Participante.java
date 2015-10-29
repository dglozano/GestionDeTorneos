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

    @OneToMany
    @JoinTable(name = "PartidosLocales",
            joinColumns = {
                    @JoinColumn(name="id_participante",unique=true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_partido")
            }
    )
    private List<Partido> partidosLocales;

    @OneToMany
    @JoinTable(name = "PartidosVisitantes",
            joinColumns = {
                    @JoinColumn(name="id_participante",unique=true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_partido")
            }
    )
    private List<Partido> partidosVisitantes;

    @OneToMany
    @JoinTable(name = "PartidosGanados",
            joinColumns = {
                    @JoinColumn(name="id_participante",unique=true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_partido")
            }
    )
    private List<Partido> partidosGanados;


    /* TODO 01: Imagen participante. Ver Blob. */


}
