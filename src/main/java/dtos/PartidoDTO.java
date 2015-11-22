package dtos;

/**
 * Created by Kevin on 15/11/2015.
 */
public class PartidoDTO {
    private int id;
    private String participanteLocal;
    private String participanteVisitante;
    private String resultado;

    public PartidoDTO(){

    }

    public String getParticipanteLocal() {
        return participanteLocal;
    }

    public String getParticipanteVisitante() {
        return participanteVisitante;
    }

    public String getResultado() {
        return resultado;
    }

    public void setParticipanteLocal(String participanteLocal) {
        this.participanteLocal = participanteLocal;
    }

    public void setParticipanteVisitante(String participanteVisitante) {
        this.participanteVisitante = participanteVisitante;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
