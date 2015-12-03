package dtos;

public class ParticipanteDTO {

    private String nombreParticipante;
    private String emailParticipante;
    private byte[] imagenParticipante;
    private boolean tieneImagen;

    public ParticipanteDTO(){
        super();
    }
    public ParticipanteDTO(String nombreParticipante, String emailParticipante) {
        super();
        this.nombreParticipante = nombreParticipante;
        this.emailParticipante = emailParticipante;
    }
    public ParticipanteDTO(String nombreParticipante, String emailParticipante, byte[] imagenParticipante) {
        super();
        this.nombreParticipante = nombreParticipante;
        this.emailParticipante = emailParticipante;
        this.imagenParticipante = imagenParticipante;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public void setNombreParticipante(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }

    public String getEmailParticipante() {
        return emailParticipante;
    }

    public void setEmailParticipante(String emailParticipante) {
        this.emailParticipante = emailParticipante;
    }

    public byte[] getImagenParticipante() {
        return imagenParticipante;
    }

    public void setImagenParticipante(byte[] imagenParticipante) {
        this.imagenParticipante = imagenParticipante;
    }

    public boolean isTieneImagen() {
        return tieneImagen;
    }

    public void setTieneImagen(boolean tieneImagen) {
        this.tieneImagen = tieneImagen;
    }
}
