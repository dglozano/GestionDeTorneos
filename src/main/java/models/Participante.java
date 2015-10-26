package models;

import javax.persistence.*;


@Entity
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_participante")
    private Integer id;
    @Column(name = "nom_partic")
    private String nombre;
    @Column(name = "puntos")
    private Integer pts;
    @Column(name = "email_partic")
    private String email;
    @Column(name = "ganados")
    private Integer ganados;
    @Column(name = "tantos_contra")
    private Integer pts_contra;
    @Column(name = "empatados")
    private Integer empatados;
    @Column(name = "tantos_favor")
    private Integer pts_favor;
    // TODO: IMAGEN PARTICIPANTE


}
