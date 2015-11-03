package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_deporte")
    private int id;
    @Column(name = "nom_deporte")
    private String nombre;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "se_practica_en",
        joinColumns = {@JoinColumn(name="id_deporte")},
            inverseJoinColumns = {@JoinColumn(name="id_lugar")})
    private List<LugarDeRealizacion> lugaresRealizacion;

    public Deporte(){
        super();
        lugaresRealizacion = new ArrayList<LugarDeRealizacion>();

    }
    public Deporte(String nombre){
        lugaresRealizacion = new ArrayList<LugarDeRealizacion>();
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
