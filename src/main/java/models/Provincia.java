package models;

import javax.persistence.*;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_provincia")
    private int id;
    @Column(name="nombre_provincia")
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name="id_pais")
    private Pais pais;


    public Provincia() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
