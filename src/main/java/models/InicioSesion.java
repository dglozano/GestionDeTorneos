package models;


import javax.persistence.*;
import java.util.Date;

@Entity
public class InicioSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_inicio_sesion")
    private int id;
    @Column(name = "ip")
    private String ip;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_elim")
    private Date fechaEliminacion;


    public InicioSesion() {
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
}
