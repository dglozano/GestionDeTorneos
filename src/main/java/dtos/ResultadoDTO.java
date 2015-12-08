package dtos;

/**
 * Created by DIego on 8/12/2015..
 */
public abstract  class ResultadoDTO {

    protected int idPartido;
    protected int idCompetencia;
    protected boolean sePresentoLocal;
    protected boolean sePresentoVisitante;

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
}
