package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_partido")
    private int id;
    @Column(name="es_libre")
    private boolean esLibre;
    @Column(name="tipo_ref_partido")
    private TipoReferenciaPartido tipoReferenciaPartido;
    @OneToMany
    @JoinColumn(name="id_partido")
    private List<Resultado> resultados;
    @ManyToOne
    @JoinColumn(name="codigo_lugar")
    private LugarDeRealizacion lugar;
    @ManyToOne(optional=true)
    @JoinTable(name = "PartidosLocales",
            joinColumns = {
                    @JoinColumn(name="id_partido")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_participante")
            }
    )
    private Participante local;
    @ManyToOne(optional=true)
    @JoinTable(name = "PartidosVisitantes",
            joinColumns = {
                    @JoinColumn(name="id_partido")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_participante")
            }
    )
    private Participante visitante;
    @ManyToOne(optional=true)
    @JoinTable(name = "PartidosGanados",
            joinColumns = {
                    @JoinColumn(name="id_partido")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_participante")
            }
    )
    private Participante ganador;
    @OneToOne
    @JoinColumn(name="id_partido")
    private Partido partidoAnteriorLocal;
    @OneToOne
    @JoinColumn(name="id_partido")
    private Partido partidoAnteriorVisitante;

    @Enumerated(EnumType.STRING)
    public TipoReferenciaPartido getTipoPartido(){
        return tipoReferenciaPartido;
    }
}
