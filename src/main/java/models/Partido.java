package models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_partido")
    @Fetch(FetchMode.SELECT)
    private List<Resultado> resultados = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="codigo_lugar")
    private LugarDeRealizacion lugar;
    @ManyToOne(optional=true, fetch = FetchType.EAGER)
    @JoinTable(name = "PartidosLocales",
            joinColumns = {
                    @JoinColumn(name="id_partido")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_participante")
            }
    )
    private Participante local;
    @ManyToOne(optional=true, fetch = FetchType.EAGER)
    @JoinTable(name = "PartidosVisitantes",
            joinColumns = {
                    @JoinColumn(name="id_partido")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_participante")
            }
    )
    private Participante visitante;
    @ManyToOne(optional=true, fetch = FetchType.EAGER)
    @JoinTable(name = "PartidosGanados",
            joinColumns = {
                    @JoinColumn(name="id_partido")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_participante")
            }
    )
    private Participante ganador;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_partido")
    private Partido partidoAnteriorLocal;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_partido")
    private Partido partidoAnteriorVisitante;

    @Enumerated(EnumType.STRING)
    public TipoReferenciaPartido getTipoPartido(){
        return tipoReferenciaPartido;
    }


    public Partido() {
    }

    public void addResultado(Resultado resultado){
        (this.resultados).add(resultado);
    }

    public int getId() {
        return id;
    }

    public boolean isEsLibre() {
        return esLibre;
    }

    public TipoReferenciaPartido getTipoReferenciaPartido() {
        return tipoReferenciaPartido;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public LugarDeRealizacion getLugar() {
        return lugar;
    }

    public Participante getLocal() {
        return local;
    }

    public Participante getVisitante() {
        return visitante;
    }

    public Participante getGanador() {
        return ganador;
    }

    public Partido getPartidoAnteriorLocal() {
        return partidoAnteriorLocal;
    }

    public Partido getPartidoAnteriorVisitante() {
        return partidoAnteriorVisitante;
    }

    public void setEsLibre(boolean esLibre) {
        this.esLibre = esLibre;
    }

    public void setTipoReferenciaPartido(TipoReferenciaPartido tipoReferenciaPartido) {
        this.tipoReferenciaPartido = tipoReferenciaPartido;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public void setLugar(LugarDeRealizacion lugar) {
        this.lugar = lugar;
    }

    public void setLocal(Participante local) {
        this.local = local;
    }

    public void setVisitante(Participante visitante) {
        this.visitante = visitante;
    }

    public void setGanador(Participante ganador) {
        this.ganador = ganador;
    }

    public void setPartidoAnteriorLocal(Partido partidoAnteriorLocal) {
        this.partidoAnteriorLocal = partidoAnteriorLocal;
    }

    public void setPartidoAnteriorVisitante(Partido partidoAnteriorVisitante) {
        this.partidoAnteriorVisitante = partidoAnteriorVisitante;
    }
}
