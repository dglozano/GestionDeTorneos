package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Competencia;
import services.GestorCompetencia;

public class Sample {

    @FXML private TextField nombreCompetencia;

    public void nuevaCompetencia(ActionEvent actionEvent) {
        GestorCompetencia gestor = new GestorCompetencia();
        Competencia c = new Competencia();

        c.setNombre(nombreCompetencia.getText());
        gestor.nuevaCompetencia(c);

        System.out.println("Agregaste la Competencia a la BD: " + nombreCompetencia.getText());
    }


}
