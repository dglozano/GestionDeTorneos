package models;

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