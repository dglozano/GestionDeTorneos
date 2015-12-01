package dtos;

/**
 * Created by DIego on 22/11/2015..
 */
public class ResultadoSetDTO {

    private int idPartido;
    private int idCompetencia;
    private boolean sePresentoLocal;
    private boolean sePresentoVisitante;
    private int[] tantosLocal = new int[9];
    private int[] tantosVisitante = new int[9];


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

    public int[] getTantosLocal() {
        return tantosLocal;
    }

    public void setTantosLocal(int[] tantosLocal) {
        this.tantosLocal = tantosLocal;
    }

    public int[] getTantosVisitante() {
        return tantosVisitante;
    }

    public void setTantosVisitante(int[] tantosVisitante) {
        this.tantosVisitante = tantosVisitante;
    }
}

