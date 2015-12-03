package dtos;

public class CompetenciaDTO {
    private int id;
    private String nombre;
    private String deporte;
    private String estado;
    private String modalidad;

    public CompetenciaDTO(int id,String nombre, String deporte, String estado, String modalidad) {
        this.id=id;
        this.nombre = nombre;
        this.deporte = deporte;
        this.estado = estado;
        this.modalidad = modalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
