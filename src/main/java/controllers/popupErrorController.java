package controllers;

import controllers.general.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class popupErrorController extends ControlledScreen {

    @FXML private Button okButton;
    @FXML private Label detailsLabel;

    @Override
    public void inicializar(String error) {
        detailsLabel.setText(error);
    }

    @Override
    public void inicializar() {
    }

    @Override
    public Object mensajeControladorAnterior(){ return null; }

    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }

}
