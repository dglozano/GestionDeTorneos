package controllers.general;

public interface ControlledScreen {
    // Permite la injecci�n del Screen Pane
    public void setScreenParent(PrincipalController screenPage);

    // Inicializa cada interfaz
    public void inicializar();

    public Object mensajeControladorAnterior();
}
