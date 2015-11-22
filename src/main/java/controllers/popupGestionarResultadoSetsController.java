package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by DIego on 21/11/2015..
 */
public class popupGestionarResultadoSetsController implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;
    private int idCompetencia;

    @FXML private Button okButton;
    @FXML private Button cancelarButton;
    @FXML private Label detailsLabel;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
    }
    public void inicializar(String mensaje){ inicializar(); }

    public Object mensajeControladorAnterior(){ return idCompetencia; }

    public void cancelar(ActionEvent actionEvent){
        myController.setControladorAnterior(this);
        Stage modal = (Stage)cancelarButton.getScene().getWindow();
        modal.close();
    }

    public void aceptar(ActionEvent actionEvent){
        myController.setControladorAnterior(this);
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }


}

