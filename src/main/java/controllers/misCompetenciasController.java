package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.GestorCompetencia;

public class misCompetenciasController implements ControlledScreen {

    PrincipalController myController;

    @FXML private TextField nombreCompetencia;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void irCrearCompetencia(ActionEvent actionEvent) {
        /* TODO 01: Como invocar metodos cuando aparece la pantalla? Descomentar siguiente linea luego*/
        // myController.setScreen(Main.vista2ID);
        GestorCompetencia gestorCompetencia = new GestorCompetencia();
        gestorCompetencia.listarTodasMisCompetencias();
    }

}
