package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class crearCompetenciasController implements ControlledScreen {

    PrincipalController myController;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    @FXML
    public void irMisCompetencias(ActionEvent actionEvent) {
        myController.setScreen(Main.vista1ID);
    }

}
