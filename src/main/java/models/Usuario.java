package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DIego on 26/10/2015.
 */
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
    @ManyToOne
    @JoinColumn(name="id_localidad")
    private Localidad localidad;
    @ManyToOne
    @JoinColumn(name="id_inicio_sesion")
    private InicioSesion inicioSesion;
    @OneToMany(mappedBy="usuario")
    private List<Competencia> competencias;
    @OneToMany(mappedBy="usuario")
    private List<LugarDeRealizacion> lugaresDeRealizacion;

    @Enumerated(EnumType.STRING)
    public TipoDocumento getTipoDocumento(){
        return tipoDocumento;
    }

}
