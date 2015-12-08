package dtos;

public class ResultadoFinalDTO extends ResultadoDTO{


    private boolean ganoLocal;
    private boolean ganoVisitante;
    private boolean empate;


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

}
