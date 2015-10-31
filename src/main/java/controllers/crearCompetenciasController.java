package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

public class crearCompetenciasController implements ControlledScreen {

    PrincipalController myController;

    @FXML private Label errorPuntuacion;
    @FXML private ToggleGroup modalidad;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void validaciones(){
        if (modalidad.getSelectedToggle() == null){
            errorPuntuacion.setText("Debe completar este campo para continuar.");
            errorPuntuacion.setVisible(true);
        } else{
            errorPuntuacion.setVisible(false);
        }
    }

    /* todavía no anda bien
    private void mostrarPopupExito(){
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/popupCompetenciaCreada.fxml"));
        final Parent root;
        try {
            root = loader.load();
            final Scene scene = new Scene(root, 250, 150);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void irMisCompetencias(ActionEvent actionEvent) {
        myController.setScreen(Main.vista1ID);
    }

    public void continuar(ActionEvent actionEvent) {
        validaciones();
    }

}
