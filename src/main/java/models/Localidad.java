package models;

import javax.persistence.*;

@Entity
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_localidad")
    private int id;
    @Column(name="nombre_localidad")
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="id_provincia")
    private Provincia provincia;


    public Localidad() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
