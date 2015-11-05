package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_competencia")
    private int id;
    @Column(name = "nom_comp")
    private String nombre;
    @Column(name = "reglas_comp",columnDefinition="TEXT")
    private String reglas;
    @Column(name = "pts_part_empatado")
    private int puntosPartidoEmpatado;
    @Column(name = "acepta_empates")
    private boolean aceptaEmpate;
    @Column(name = "pts_por_presentarse")
    private int puntosPorPresentarse;
    @Column(name = "tantos_a_favor_por_no_presentarse")
    private int tantosFavorNoPresentarse;
    @Column(name = "pts_partido_ganado")
    private int puntosPartidoGanado;
    @Column(name = "eliminada")
    private boolean eliminada;
    @Column(name = "otorga_tantos")
    private boolean otorgaTantosNoPresentarse;
    @Column(name = "fecha_elim")
    @Temporal(TemporalType.DATE)
    private Date fechaEliminada;
    @Column(name = "cant_sets")
    private int cantidadDeSets;
    @Column(name = "modalidad")
    private Modalidad modalidad;
    @Column(name = "Estado")
    private Estado estado;
    @Column (name = "sistemaPuntuacion")
    private SistemaPuntuacion sistemaPuntuacion;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_competencia")
    private List<Participante> participantes;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "id_deporte")
    private Deporte deporte;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_competencia")
    private List<Disponibilidad> disponibilidades = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "id_usuario")
    private Usuario usuario;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_fixture")
    private Fixture fixture;

    public Competencia() {
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReglas() {
        return reglas;
    }

    public void setReglas(String reglas) {
        this.reglas = reglas;
    }

    public int getPuntosPartidoEmpatado() {
        return puntosPartidoEmpatado;
    }

    public void setPuntosPartidoEmpatado(int puntosPartidoEmpatado) {
        this.puntosPartidoEmpatado = puntosPartidoEmpatado;
    }

    public boolean isAceptaEmpate() {
        return aceptaEmpate;
    }

    public void setAceptaEmpate(boolean aceptaEmpate) {
        this.aceptaEmpate = aceptaEmpate;
    }

    public int getPuntosPorPresentarse() {
        return puntosPorPresentarse;
    }

    public void setPuntosPorPresentarse(int puntosPorPresentarse) {
        this.puntosPorPresentarse = puntosPorPresentarse;
    }

    public int getTantosFavorNoPresentarse() {
        return tantosFavorNoPresentarse;
    }

    public void setTantosFavorNoPresentarse(int tantosFavorNoPresentarse) {
        this.tantosFavorNoPresentarse = tantosFavorNoPresentarse;
    }

    public int getPuntosPartidoGanado() {
        return puntosPartidoGanado;
    }

    public void setPuntosPartidoGanado(int puntosPartidoGanado) {
        this.puntosPartidoGanado = puntosPartidoGanado;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }

    public Date getFechaEliminada() {
        return fechaEliminada;
    }

    public void setFechaEliminada(Date fechaEliminada) {
        this.fechaEliminada = fechaEliminada;
    }

    public int getCantidadDeSets() {
        return cantidadDeSets;
    }

    public void setCantidadDeSets(int cantidadDeSets) {
        this.cantidadDeSets = cantidadDeSets;
    }

    @Enumerated(EnumType.STRING)
    public Modalidad getModalidad(){
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    @Enumerated(EnumType.STRING)
    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Enumerated(EnumType.STRING)
    public SistemaPuntuacion getSistemaPuntuacion() {
        return sistemaPuntuacion;
    }

    public void setSistemaPuntuacion(SistemaPuntuacion sistemaPuntuacion) {
        this.sistemaPuntuacion = sistemaPuntuacion;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public void addParticipante(Participante participante){
        this.participantes.add(participante);
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public void addDisponibilidad(Disponibilidad disponibilidad){
        this.disponibilidades.add(disponibilidad);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isOtorgaTantosNoPresentarse() {
        return otorgaTantosNoPresentarse;
    }

    public void setOtorgaTantosNoPresentarse(boolean otorgaTantosNoPresentarse) {
        this.otorgaTantosNoPresentarse = otorgaTantosNoPresentarse;
    }


}

