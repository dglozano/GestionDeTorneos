package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;

/**
 * Created by DIego on 11/11/2015..
 */
public class listarParticipantesController implements ControlledScreen {

    private int idCompetencia;
    private PrincipalController myController;

    public void inicializar(){
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
    }

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId);
    }

    public void irAltaParticipante(ActionEvent actionEvent){
        myController.setScreen(Main.vistaAltaParticipanteId);
    }

    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

}
