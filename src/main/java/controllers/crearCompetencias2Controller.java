package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.scene.Parent;
import javafx.stage.Stage;
import services.GestorCompetencia;
import services.GestorDeporte;
import services.GestorLugarRealizacion;

public class crearCompetencias2Controller implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;
    private GestorLugarRealizacion gestorLugarRealizacion;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        gestorLugarRealizacion = new GestorLugarRealizacion();

        String test = (String)myController.getControladorAnterior().mensajeControladorAnterior();
        System.out.println(test);
    }

    public Object mensajeControladorAnterior(){
        return null;
    };
}