package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class misCompetenciasController implements ControlledScreen {

    PrincipalController myController;

    @FXML private TextField nombreCompetencia;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    @FXML
    public void irCrearCompetencia(ActionEvent actionEvent) {
        /*GestorCompetencia gestor = new GestorCompetencia();
        Competencia c = new Competencia();

        c.setNombre(nombreCompetencia.getText());
        c.setEstado(Estado.CREADA);
        c.setModalidad(Modalidad.ELIM_DOBLE);
        c.setSistemaPuntuacion(SistemaPuntuacion.PUNTUACION);
        gestor.nuevaCompetencia(c);*/

        myController.setScreen(Main.vista2ID);
        //System.out.println("Agregaste la Competencia a la BD: " + nombreCompetencia.getText());
    }

}
