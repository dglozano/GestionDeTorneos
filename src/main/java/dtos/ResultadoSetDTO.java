package dtos;

public class ResultadoSetDTO extends ResultadoDTO{

    private int[] tantosLocal = new int[9];
    private int[] tantosVisitante = new int[9];

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

