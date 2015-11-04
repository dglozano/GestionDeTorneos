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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Modalidad;
import models.SistemaPuntuacion;
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
    @FXML private Spinner ptsGanadosSpinner;
    @FXML private Spinner ptsPorPresentarseSpinner;
    @FXML private Spinner tantosOtorgadosSpinner;
    @FXML private Spinner ptsEmpateSpinner;
    @FXML private Label ptsGanadosLabel;
    @FXML private Label ptsEmpateLabel;
    @FXML private Label ptsPorPresentarseLabel;
    @FXML private Label tantosOtorgadosLabel;
    @FXML private Label permiteEmpateLabel;
    @FXML private ToggleGroup permiteEmpateToggleGroup;
    @FXML private RadioButton siRadioButton;
    @FXML private RadioButton noRadioButton;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        gestorLugarRealizacion = new GestorLugarRealizacion();
        datosCrearCompetenciaDtoAnterior = (DatosCrearCompetenciaDTO) myController.getControladorAnterior().mensajeControladorAnterior();
        cargarLugares();
        cargarSpinners();
    }

    private void cargarSpinners() {
        ptsGanadosSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        ptsEmpateSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        ptsPorPresentarseSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        tantosOtorgadosSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        setearDisables();
    }

    private void setearDisables() {
        if(permiteEmpateToggleGroup.getSelectedToggle() != null)
            permiteEmpateToggleGroup.getSelectedToggle().setSelected(false);
        boolean esElimSimple = datosCrearCompetenciaDtoAnterior.getModalidad().equals(Modalidad.ELIM_SIMPLE);
        boolean esElimDoble = datosCrearCompetenciaDtoAnterior.getModalidad().equals(Modalidad.ELIM_DOBLE);
        if(esElimDoble || esElimSimple){
            disableAll(true);
        }
        else{
            SistemaPuntuacion puntuacion = datosCrearCompetenciaDtoAnterior.getPuntuacion();
            if(puntuacion.equals(SistemaPuntuacion.RESULTADO_FINAL)){
                disableAll(false);
                tantosOtorgadosSpinner.setDisable(true);
                tantosOtorgadosLabel.setDisable(true);
                ptsEmpateLabel.setDisable(true);
                ptsEmpateSpinner.setDisable(true);
            }
            if(puntuacion.equals(SistemaPuntuacion.SET)){
                disableAll(true);
                ptsGanadosSpinner.setDisable(false);
                ptsGanadosLabel.setDisable(false);
                ptsPorPresentarseSpinner.setDisable(false);
                ptsPorPresentarseLabel.setDisable(false);
            }
        }
    }

    private void disableAll(boolean flag) {
        ptsPorPresentarseSpinner.setDisable(flag);
        ptsGanadosSpinner.setDisable(flag);
        ptsEmpateSpinner.setDisable(flag);
        tantosOtorgadosSpinner.setDisable(flag);
        permiteEmpateLabel.setDisable(flag);
        ptsEmpateLabel.setDisable(flag);
        ptsGanadosLabel.setDisable(flag);
        ptsPorPresentarseLabel.setDisable(flag);
        tantosOtorgadosLabel.setDisable(flag);
        noRadioButton.setDisable(flag);
        siRadioButton.setDisable(flag);
    }

    private void cargarLugares() {
        List<DisponibilidadLugar> filas = new ArrayList<>();
        for(String nombreLugar: datosCrearCompetenciaDtoAnterior.getListaLugaresNombres()){
            DisponibilidadLugar disponibilidadLugar= new DisponibilidadLugar();
            disponibilidadLugar.setDisponibilidad(Integer.parseInt("0"));
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

    public void aceptaEmpateSelected(ActionEvent actionEvent){
        String source = ((RadioButton)actionEvent.getSource()).getText();
        if(source.equals("Si")){
            ptsEmpateLabel.setDisable(false);
            ptsEmpateSpinner.setDisable(false);
        }
        else{
            ptsEmpateLabel.setDisable(true);
            ptsEmpateSpinner.setDisable(true);
        }
    }

}