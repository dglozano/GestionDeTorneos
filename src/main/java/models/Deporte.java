package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Kevincho on 20/10/2015.
 */

@Entity
public class Deporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_deporte")
    private Integer id;
    @Column(name = "nom_deporte")
    private String nombre;

}
