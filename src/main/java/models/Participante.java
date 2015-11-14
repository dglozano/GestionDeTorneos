package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Participante {

    public static final String LIBRE = "LIBRE";

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

    private boolean esLibre;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_participante")
    private List<ModificacionParticipante> modificacionesParticipante = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PartidosLocales",
            joinColumns = {
                    @JoinColumn(name="id_participante",unique=true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_partido")
            }
    )
    private List<Partido> partidosLocales = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PartidosVisitantes",
            joinColumns = {
                    @JoinColumn(name="id_participante",unique=true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_partido")
            }
    )
    private List<Partido> partidosVisitantes=new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PartidosGanados",
            joinColumns = {
                    @JoinColumn(name="id_participante",unique=true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name="id_partido")
            }
    )
    private List<Partido> partidosGanados = new ArrayList<>();

    @Lob
    @Column(name = "Imagen")
    private byte[] imagen;


    public Participante() {
        super();
    }

    public Participante(String esLibre){
        super();
        if(esLibre.equals(Participante.LIBRE)){
            setEsLibre(true);
        }
    }

    public void addModificacionParticipante(ModificacionParticipante modificacion) {
        (this.modificacionesParticipante).add(modificacion);
    }

    public void addPartidoLocal(Partido p){
        this.partidosLocales.add(p);
    }
    public void addPartidoVisitante(Partido p){
        this.partidosVisitantes.add(p);
    }
    public void addPartidoGanador(Partido p){
        this.partidosGanados.add(p);
    }
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEsLibre() {
        return esLibre;
    }

    public List<ModificacionParticipante> getModificacionesParticipante() {
        return modificacionesParticipante;
    }

    public List<Partido> getPartidosLocales() {
        return partidosLocales;
    }

    public List<Partido> getPartidosVisitantes() {
        return partidosVisitantes;
    }

    public List<Partido> getPartidosGanados() {
        return partidosGanados;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEsLibre(boolean esLibre) {
        this.esLibre = esLibre;
    }

    public void setModificacionesParticipante(List<ModificacionParticipante> modificacionesParticipante) {
        this.modificacionesParticipante = modificacionesParticipante;
    }

    public void setPartidosLocales(List<Partido> partidosLocales) {
        this.partidosLocales = partidosLocales;
    }

    public void setPartidosVisitantes(List<Partido> partidosVisitantes) {
        this.partidosVisitantes = partidosVisitantes;
    }

    public void setPartidosGanados(List<Partido> partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
