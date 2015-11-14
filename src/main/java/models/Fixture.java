package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class Fixture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_fixture")
    private int id;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="id_fixture")
    private List<Fecha> fechas = new ArrayList<>();

    public Fixture() {
    }

    public void addFecha(Fecha fecha){
        (this.fechas).add(fecha);
    }

    public int getId() {
        return id;
    }

    public List<Fecha> getFechas() {
        return fechas;
    }

    public void setFechas(List<Fecha> fechas) {
        this.fechas = fechas;
    }
}
