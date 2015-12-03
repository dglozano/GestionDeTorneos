package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class popupErrorController extends ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;

    @FXML private Button okButton;
    @FXML private Label detailsLabel;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(String error) {
        detailsLabel.setText(error);
    }
    public void inicializar() {
    }

    public Object mensajeControladorAnterior(){ return null; }

    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }

}
