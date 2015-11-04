package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.DatosCrearCompetenciaDTO;
import dtos.DisponibilidadLugar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.GestorCompetencia;
import services.GestorDeporte;
import services.GestorLugarRealizacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class crearCompetencias2Controller implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;
    private GestorLugarRealizacion gestorLugarRealizacion;
    private DatosCrearCompetenciaDTO datosCrearCompetenciaDtoAnterior;

    @FXML private TableView<DisponibilidadLugar> tablaDisponibilidad;
    @FXML private TableColumn<DisponibilidadLugar,String> columnaLugar;
    @FXML private TableColumn<DisponibilidadLugar,String> columnaDisponibilidad;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        gestorLugarRealizacion = new GestorLugarRealizacion();
        datosCrearCompetenciaDtoAnterior = (DatosCrearCompetenciaDTO) myController.getControladorAnterior().mensajeControladorAnterior();
        cargarLugares();
    }

    private void cargarLugares() {
        List<DisponibilidadLugar> filas = new ArrayList<>();
        int i = 0;
        for(String nombreLugar: datosCrearCompetenciaDtoAnterior.getListaLugaresNombres()){
            DisponibilidadLugar disponibilidadLugar= new DisponibilidadLugar();
            disponibilidadLugar.setDisponibilidad(Integer.toString(i++));
            disponibilidadLugar.setNombreLugar(nombreLugar);
            filas.add(disponibilidadLugar);
        }
        columnaLugar.setCellValueFactory(new PropertyValueFactory<DisponibilidadLugar,String>("nombreLugar"));
        columnaDisponibilidad.setCellValueFactory(new PropertyValueFactory<DisponibilidadLugar,String>("disponibilidad"));
        tablaDisponibilidad.getItems().setAll(filas);
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