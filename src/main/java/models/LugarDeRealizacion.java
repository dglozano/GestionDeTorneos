package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class LugarDeRealizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_lugar")
    private int id;
    @Column(name = "codigo_lugar")
    private String codigo;
    @Column(name = "nom_lugar")
    private String nombre;
    @Column(name = "descripcion_lugar")
    private String descripcion;
    @Column(name = "eliminado")
    private boolean eliminado;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_elim")
    private Date fecha_elim;
    @ManyToMany(mappedBy="lugaresRealizacion",fetch = FetchType.EAGER)
    private List<Deporte> deportes;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;


    public LugarDeRealizacion() {
        deportes = new ArrayList<Deporte>();
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public Date getFecha_elim() {
        return fecha_elim;
    }

    public List<Deporte> getDeportes() {
        return deportes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setFecha_elim(Date fecha_elim) {
        this.fecha_elim = fecha_elim;
    }

    public void setDeportes(List<Deporte> deportes) {
        this.deportes = deportes;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void addDeporte(Deporte deporte){
        deportes.add(deporte);
    }
}
