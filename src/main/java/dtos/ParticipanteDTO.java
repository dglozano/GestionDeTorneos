package dtos;

import javafx.scene.image.Image;

/**
 * Created by DIego on 14/11/2015..
 */
public class ParticipanteDTO {

    private String nombreParticipante;
    private String emailParticipante;
    private Image imagenParticipante;
    private boolean tieneImagen;

    public ParticipanteDTO(){
        super();
    }
    public ParticipanteDTO(String nombreParticipante, String emailParticipante) {
        super();
        this.nombreParticipante = nombreParticipante;
        this.emailParticipante = emailParticipante;
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

    public Image getImagenParticipante() {
        return imagenParticipante;
    }

    public void setImagenParticipante(Image imagenParticipante) {
        this.imagenParticipante = imagenParticipante;
    }

    public boolean isTieneImagen() {
        return tieneImagen;
    }

    public void setTieneImagen(boolean tieneImagen) {
        this.tieneImagen = tieneImagen;
    }
}
