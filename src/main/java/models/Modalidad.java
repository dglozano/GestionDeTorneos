package models;

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