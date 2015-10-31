package models;

import javax.persistence.Entity;

/**
 * Created by Kevincho on 22/10/2015.
 */

public enum Modalidad{
    LIGA("Liga"), ELIM_SIMPLE("Eliminatoria Simple"), ELIM_DOBLE ("Eliminatoria Doble");


    private String modalidadString;

    Modalidad(String modalidadString){
        this.modalidadString=modalidadString;
    }

    public String getModalidadString(){
        return  modalidadString;
    }
}