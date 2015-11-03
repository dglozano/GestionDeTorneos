package dtos;

/**
 * Created by DIego on 2/11/2015..
 */
public class LugarYCodigoDTO {

    private String nombreLugar;
    private int idLugar;

    public LugarYCodigoDTO(String nombreLugar, int idLugar) {
        this.nombreLugar = nombreLugar;
        this.idLugar = idLugar;
    }

    public String getNombreLugar() {

        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setCodigoLugar(int idLugar) {
        this.idLugar = idLugar;
    }
}
