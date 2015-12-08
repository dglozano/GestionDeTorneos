package dtos;

public class ResultadoPuntuacionDTO extends ResultadoDTO{

    private int tantosLocal;
    private int tantosVisitante;
    private boolean tieneDesempate;
    private boolean ganoLocalDesempate;

    public boolean isTieneDesempate() {
        return tieneDesempate;
    }

    public void setTieneDesempate(boolean tieneDesempate) {
        this.tieneDesempate = tieneDesempate;
    }

    public boolean isGanoLocalDesempate() {
        return ganoLocalDesempate;
    }

    public void setGanoLocalDesempate(boolean ganoLocalDesempate) {
        this.ganoLocalDesempate = ganoLocalDesempate;
    }

    public int getTantosLocal() {
        return tantosLocal;
    }

    public void setTantosLocal(int tantosLocal) {
        this.tantosLocal = tantosLocal;
    }

    public int getTantosVisitante() {
        return tantosVisitante;
    }

    public void setTantosVisitante(int tantosVisitante) {
        this.tantosVisitante = tantosVisitante;
    }
}
