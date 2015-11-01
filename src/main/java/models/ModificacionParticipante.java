package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DIego on 26/10/2015.
 */
@Entity
public class ModificacionParticipante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_modif_participante")
    private int id;
    @Column(name="nombre_participante")
    private String nombre;
    @Column(name="email")
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_modificacion")
    private Date fechaModificacion;
    @Lob
    @Column(name = "Imagen")
    private byte[] imagen;

    public ModificacionParticipante() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
