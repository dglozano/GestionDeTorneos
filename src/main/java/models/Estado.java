package models;

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



