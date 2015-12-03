package controllers.general;

import dao.util.MiEntityManager;
import javafx.application.Platform;

abstract public class ControlledScreen {
    // Permite la injección del Screen Pane
    abstract public void setScreenParent(PrincipalController screenPage);

    // Inicializa cada interfaz
    abstract public void inicializar();
    abstract public void inicializar(String mensaje);

    abstract public Object mensajeControladorAnterior();

    public void cerrarPrograma(){
        MiEntityManager.close();
        Platform.exit();
        System.exit(0);
    }
}
