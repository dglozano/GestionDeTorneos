package dtos;

/**
 * Created by DIego on 21/11/2015..
 */
public class ResultadoFinalDTO {

    private int idCompetencia;
    private int idPartido;
    private boolean ganoLocal;
    private boolean ganoVisitante;
    private boolean empate;
    private boolean sePresentoLocal;
    private boolean sePresentoVisitante;

    public int getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(int idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public boolean isGanoLocal() {
        return ganoLocal;
    }

    public void setGanoLocal(boolean ganoLocal) {
        this.ganoLocal = ganoLocal;
    }

    public boolean isGanoVisitante() {
        return ganoVisitante;
    }

    public void setGanoVisitante(boolean ganoVisitante) {
        this.ganoVisitante = ganoVisitante;
    }

    public boolean isEmpate() {
        return empate;
    }

    public void setEmpate(boolean empate) {
        this.empate = empate;
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
