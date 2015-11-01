package models;

import javax.persistence.*;
import java.util.List;


@Entity
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_deporte")
    private int id;
    @Column(name = "nom_deporte")
    private String nombre;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name= "se_practica_en",
        joinColumns = {@JoinColumn(name="id_deporte", unique=true)},
            inverseJoinColumns = {@JoinColumn(name="codigo_lugar")})
    private List<LugarDeRealizacion> lugaresRealizacion;

    public Deporte(){
        super();
    }
    public Deporte(String nombre){
        this.nombre= nombre;
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

    public List<LugarDeRealizacion> getLugaresRealizacion() {
        return lugaresRealizacion;
    }

    public void setLugaresRealizacion(List<LugarDeRealizacion> lugaresRealizacion) {
        this.lugaresRealizacion = lugaresRealizacion;
    }

    public void addLugarDeRealizacion(LugarDeRealizacion lugar){
        (this.lugaresRealizacion).add(lugar);
    }
}
