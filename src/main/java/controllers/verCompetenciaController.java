package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.CompetenciaDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.GestorCompetencia;



/**
 * Created by Kevin on 09/11/2015.
 */
public class verCompetenciaController implements ControlledScreen{

    private PrincipalController myController;
    private GestorCompetencia gestorCompetencia;
    private int idCompetencia;

    @FXML private TextField modalidadTextField;
    @FXML private TextField deporteTextField;
    @FXML private TextField estadoTextField;
    @FXML private TextField proximoEncuentroTextField;
    @FXML private Text title;


    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }


   public void inicializar() {
        idCompetencia= (Integer)myController.getControladorAnterior().mensajeControladorAnterior();
        gestorCompetencia = new GestorCompetencia();
        CompetenciaDTO competencia = gestorCompetencia.getCompetencia(idCompetencia);
        title.setText(competencia.getNombre());
        deporteTextField.setText(competencia.getDeporte());
        modalidadTextField.setText(competencia.getModalidad());
        estadoTextField.setText(competencia.getEstado());
        /* TODO 02: setear proximo encuentro cuando funcione fixture */
    }


    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaMisCompetenciasId);
    }

    public void verTablaPosiciones(ActionEvent actionEvent){
        myController.setScreen(Main.vistaTablaPosicionesId);
    }


}
