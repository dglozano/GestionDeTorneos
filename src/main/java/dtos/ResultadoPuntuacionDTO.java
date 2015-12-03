package dtos;

public class ResultadoPuntuacionDTO {

    private int idPartido;
    private int idCompetencia;
    private boolean sePresentoLocal;
    private boolean sePresentoVisitante;
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

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(int idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public boolean isSePresentoLocal() {
        return sePresentoLocal;
    }

    public void setSePresentoLocal(boolean sePresentoLocal) {
        this.sePresentoLocal = sePresentoLocal;
    }

    public boolean isSePresentoVisitante() {
        return sePresentoVisitante;
    }

    public void setSePresentoVisitante(boolean sePresentoVisitante) {
        this.sePresentoVisitante = sePresentoVisitante;
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
