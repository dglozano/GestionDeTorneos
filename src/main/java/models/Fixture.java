package models;

import javax.persistence.*;
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
    @OneToMany
    @JoinColumn(name="id_fixture")
    private List<Fecha> fechas;
}
