package models;

import javax.persistence.*;


@Entity
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_disponibilidad")
    private int id;
    @Column(name = "disponibilidad")
    private int disponibilidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_lugar")
    private LugarDeRealizacion lugarDeRealizacion;


    public Disponibilidad() {
    }

    public int getId() {
        return id;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public LugarDeRealizacion getLugarDeRealizacion() {
        return lugarDeRealizacion;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setLugarDeRealizacion(LugarDeRealizacion lugarDeRealizacion) {
        this.lugarDeRealizacion = lugarDeRealizacion;
    }

    public void decrementarDisponibilidad(int dec){
        this.disponibilidad-=dec;
    }
}
