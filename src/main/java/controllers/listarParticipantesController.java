package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import models.Competencia;
import services.GestorCompetencia;

/**
 * Created by DIego on 11/11/2015..
 */
public class listarParticipantesController implements ControlledScreen {

    private int idCompetencia;
    private Competencia competencia;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private PrincipalController myController;

    @FXML private Text title;

    public void inicializar(){
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
        title.setText(competencia.getNombre());
    }
    public void inicializar(String mensaje) {inicializar();};

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
