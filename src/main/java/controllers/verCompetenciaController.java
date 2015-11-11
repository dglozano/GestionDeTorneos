package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.CompetenciaDTO;
import javafx.fxml.FXML;
import models.Modalidad;
import services.GestorCompetencia;

import javax.xml.soap.Text;
import java.awt.*;

/**
 * Created by Kevin on 09/11/2015.
 */
public class verCompetenciaController implements ControlledScreen{

    private PrincipalController myController;
    private GestorCompetencia gestorCompetencia;

    @FXML private TextField modalidadTextField;
    @FXML private TextField deporteTextField;
    @FXML private TextField estadoTextField;
    @FXML private TextField proximoEncuentroTextField;
    @FXML private Text title;


    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }


   public void inicializar() {
        int compId = (Integer)myController.getControladorAnterior().mensajeControladorAnterior();
        gestorCompetencia = new GestorCompetencia();
        CompetenciaDTO competencia = gestorCompetencia.getCompetencia(compId);
        title.setValue(competencia.getNombre());
        deporteTextField.setText(competencia.getDeporte());
        modalidadTextField.setText(competencia.getModalidad());
        estadoTextField.setText(competencia.getEstado());
        /* TODO 02: setear proximo encuentro cuando funcione fixture */
    }


    public Object mensajeControladorAnterior(){
        return null;
    }
}
