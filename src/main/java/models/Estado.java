package models;

import javax.persistence.Entity;

/**
 * Created by Kevincho on 22/10/2015.
 */
public enum Estado{
    CREADA("Creada"), PLANIFICADA("Planificada"), EN_DISPUTA("En disputa"), FINALIZADA("Finalizada"), ELIMINADA("Eliminada");

    private String estadoString;

    Estado(String estadoString){
        this.estadoString=estadoString;
    }

    public String getEstadoString(){
        return  estadoString;
    }
}



