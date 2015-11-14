package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class popupEnDesarrolloController implements ControlledScreen {
    private PrincipalController myController;
    private Stage modal;
    private Parent parent;

    @FXML private Button okButton;
    @FXML private Label detailsLabel;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar() {};
    public void inicializar(String mensaje){ inicializar(); };

    public Object mensajeControladorAnterior(){ return null; }

    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }

}
