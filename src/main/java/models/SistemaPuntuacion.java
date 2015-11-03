package models;

import javax.persistence.Entity;

/**
 * Created by Kevincho on 22/10/2015.
 */
public enum SistemaPuntuacion{
    SET("Sets"), PUNTUACION("Puntos"), RESULTADO_FINAL("Resultado Final");

    private String puntuacionString;

    SistemaPuntuacion(String puntuacionString){
        this.puntuacionString=puntuacionString;
    }
    public String getPuntuacionString(){
        return  puntuacionString;
    }
}