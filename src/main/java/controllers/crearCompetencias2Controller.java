package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.DatosCrearCompetenciaDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.GestorCompetencia;
import services.GestorDeporte;
import services.GestorLugarRealizacion;

import java.io.IOException;

public class crearCompetencias2Controller implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;
    private GestorLugarRealizacion gestorLugarRealizacion;
    private DatosCrearCompetenciaDTO datosCrearCompetenciaDtoAnterior;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        gestorLugarRealizacion = new GestorLugarRealizacion();

        datosCrearCompetenciaDtoAnterior = (DatosCrearCompetenciaDTO) myController.getControladorAnterior().mensajeControladorAnterior();
        System.out.println("nombre " + datosCrearCompetenciaDtoAnterior.getCompetencia());
        System.out.println("deporte " + datosCrearCompetenciaDtoAnterior.getDeporte().getNombre());
        System.out.println("modalidad " + datosCrearCompetenciaDtoAnterior.getModalidad().getModalidadString());
        System.out.println("puntuacion " + datosCrearCompetenciaDtoAnterior.getPuntuacion().getPuntuacionString());
        for(String id: datosCrearCompetenciaDtoAnterior.getListaLugaresId()){
            System.out.println("lugar "+ id);
        }
        if(datosCrearCompetenciaDtoAnterior.isTieneReglamento()){
            System.out.println("reglamento "+datosCrearCompetenciaDtoAnterior.getReglamento());
        }
        if(datosCrearCompetenciaDtoAnterior.isTieneSets()){
            System.out.println("sets " + datosCrearCompetenciaDtoAnterior.getSets());
        }
    }

    private void mostrarPopupExito(){
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/popupCompetenciaCreada.fxml"));
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.initStyle(StageStyle.TRANSPARENT);
            modal.setScene(scene);
            modal.setResizable(false);
            modal.sizeToScene();
            modal.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object mensajeControladorAnterior(){
        return datosCrearCompetenciaDtoAnterior;
    };

    public void irAlPasoUno(ActionEvent actionEvent){
        myController.setScreen(Main.vista2ID,this);
    }


}