package models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private int id;
    @Column(name = "num_doc")
    private String numeroDocumento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name="apellido")
    private String apellido;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="tipo_doc")
    private TipoDocumento tipoDocumento;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_localidad")
    private Localidad localidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_inicio_sesion")
    private InicioSesion inicioSesion;
    @OneToMany(mappedBy="usuario", fetch = FetchType.LAZY)
    private List<Competencia> competencias;
    @OneToMany(mappedBy="usuario", fetch = FetchType.LAZY)
    private List<LugarDeRealizacion> lugaresDeRealizacion;
    @Enumerated(EnumType.STRING)
    public TipoDocumento getTipoDocumento(){
        return tipoDocumento;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public InicioSesion getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(InicioSesion inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public List<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(List<Competencia> competencias) {
        this.competencias = competencias;
    }

    public List<LugarDeRealizacion> getLugaresDeRealizacion() {
        return lugaresDeRealizacion;
    }

    public void setLugaresDeRealizacion(List<LugarDeRealizacion> lugaresDeRealizacion) {
        this.lugaresDeRealizacion = lugaresDeRealizacion;
    }

    public void addCompetencia(Competencia competencia){
        (this.competencias).add(competencia);
    }

    public void addLugarDeRealizacion(LugarDeRealizacion lugar){
        (this.lugaresDeRealizacion).add(lugar);
    }
}
