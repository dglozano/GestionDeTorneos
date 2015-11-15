package dtos;

/**
 * Created by Kevin on 15/11/2015.
 */
public class PartidoDTO {
    private String partiLocal;
    private String partiVisit;
    private String result;

    public PartidoDTO(){

    }

    public String getPartiLocal() {
        return partiLocal;
    }

    public String getPartiVisit() {
        return partiVisit;
    }

    public String getResult() {
        return result;
    }

    public void setPartiLocal(String partiLocal) {
        this.partiLocal = partiLocal;
    }

    public void setPartiVisit(String partiVisit) {
        this.partiVisit = partiVisit;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
