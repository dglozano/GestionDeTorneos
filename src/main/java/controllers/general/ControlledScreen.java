package controllers.general;

import dao.util.MiEntityManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

abstract public class ControlledScreen {
    // Permite la injección del Screen Pane
    protected PrincipalController myController;
    protected Stage modal;
    protected Parent parent;

    public void setScreenParent(PrincipalController screenPage){
        this.myController=screenPage;
    }

    // Inicializa cada interfaz
    abstract public void inicializar();

    public void inicializar(String mensaje){
        inicializar();
    }

    abstract public Object mensajeControladorAnterior();

    public void cerrarPrograma(){
        MiEntityManager.close();
        Platform.exit();
        System.exit(0);
    }

    public void registrarUsuario(){
        mostrarPopUp("Esta funcionalidad esta en desarrollo","desarrollo");
    }

    public PrincipalController getMyController() {
        return myController;
    }

    public void setMyController(PrincipalController myController) {
        this.myController = myController;
    }

    public void mostrarPopUp(){
        mostrarPopUp("","");
    }

    public void mostrarPopUp(String mensaje, String tipo){
        String recurso;
        switch(tipo){
            case "error":
                recurso = "fxml/popupError.fxml";
                break;
            case "exito":
                recurso = "fxml/popupExito.fxml";
                break;
            default:
                recurso = "fxml/popupEnDesarrollo.fxml";
                break;
        }
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(recurso));
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            ControlledScreen myScreenControler = ((ControlledScreen) loader.getController());
            myScreenControler.setScreenParent(myController);
            myScreenControler.inicializar(mensaje);
            modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.TRANSPARENT);
            modal.setScene(scene);
            modal.setResizable(false);
            modal.sizeToScene();
            modal.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
