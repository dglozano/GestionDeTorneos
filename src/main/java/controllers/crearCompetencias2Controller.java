package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.SpinnerCell;
import dtos.DatosCrearCompetenciaDTO;
import dtos.DatosCrearCompetenciaPaso2DTO;
import dtos.DisponibilidadLugar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Modalidad;
import models.SistemaPuntuacion;
import services.GestorCompetencia;

import java.util.ArrayList;
import java.util.List;

public class crearCompetencias2Controller extends ControlledScreen {

    private GestorCompetencia gestorCompetencia;
    private DatosCrearCompetenciaDTO datosCrearCompetenciaDtoAnterior;

    @FXML private TableView<DisponibilidadLugar> tablaDisponibilidad;
    @FXML private TableColumn<DisponibilidadLugar,String> columnaLugar;
    @FXML private TableColumn<DisponibilidadLugar,Integer> columnaDisponibilidad;
    @FXML private Spinner ptsGanadosSpinner;
    @FXML private Spinner ptsPorPresentarseSpinner;
    @FXML private Spinner tantosOtorgadosSpinner;
    @FXML private Spinner ptsEmpateSpinner;
    @FXML private Label ptsGanadosLabel;
    @FXML private Label ptsEmpateLabel;
    @FXML private Label ptsPorPresentarseLabel;
    @FXML private Label tantosOtorgadosLabel;
    @FXML private Label permiteEmpateLabel;
    @FXML private Label errorPuntuacion1;
    @FXML private Label errorPuntuacion2;
    @FXML private ToggleGroup permiteEmpateToggleGroup;
    @FXML private RadioButton siRadioButton;
    @FXML private RadioButton noRadioButton;
    @FXML private Label detailsLabel;

    @Override
    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        datosCrearCompetenciaDtoAnterior = (DatosCrearCompetenciaDTO) myController.getControladorAnterior().mensajeControladorAnterior();
        errorPuntuacion1.setVisible(false);
        errorPuntuacion2.setVisible(false);
        cargarLugares();
        cargarSpinners();
    }

    private void cargarSpinners() {
        ptsGanadosSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        ptsEmpateSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        ptsPorPresentarseSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        tantosOtorgadosSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
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
            if(puntuacion.equals(SistemaPuntuacion.PUNTUACION)){
                disableAll(false);
                ptsEmpateLabel.setDisable(true);
                ptsEmpateSpinner.setDisable(true);
                noRadioButton.setSelected(true);
            }
            if(puntuacion.equals(SistemaPuntuacion.RESULTADO_FINAL)){
                disableAll(false);
                tantosOtorgadosSpinner.setDisable(true);
                tantosOtorgadosLabel.setDisable(true);
                ptsEmpateLabel.setDisable(true);
                ptsEmpateSpinner.setDisable(true);
                noRadioButton.setSelected(true);
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
        tablaDisponibilidad.getItems().clear();
        for(String nombreLugar: datosCrearCompetenciaDtoAnterior.getListaLugaresNombres()){
            DisponibilidadLugar disponibilidadLugar= new DisponibilidadLugar();
            disponibilidadLugar.setDisponibilidad(1);
            disponibilidadLugar.setNombreLugar(nombreLugar);
            filas.add(disponibilidadLugar);
        }

        columnaLugar.setCellValueFactory(new PropertyValueFactory<DisponibilidadLugar, String>("nombreLugar"));

        Callback<TableColumn<DisponibilidadLugar, Integer>, TableCell<DisponibilidadLugar, Integer>> spinnerCellFactory =
                new Callback<TableColumn<DisponibilidadLugar, Integer>, TableCell<DisponibilidadLugar, Integer>>() {
                    @Override
                    public TableCell<DisponibilidadLugar, Integer> call(TableColumn<DisponibilidadLugar, Integer> p) {
                        return new SpinnerCell<DisponibilidadLugar,Integer>();
                    }
                };
        columnaDisponibilidad.setCellFactory(spinnerCellFactory);
        columnaDisponibilidad.setCellValueFactory(new PropertyValueFactory<DisponibilidadLugar, Integer>("disponibilidad"));

        tablaDisponibilidad.setEditable(true);
        tablaDisponibilidad.getItems().setAll(filas);
    }

    @Override
    public Object mensajeControladorAnterior(){
        return datosCrearCompetenciaDtoAnterior;
    };

    public void irAlPasoUno(ActionEvent actionEvent){
        myController.setScreen(Main.vistaCrearCompetenciaPasoUnoId,this);
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

    public void crearCompetencia(ActionEvent actionEvent){
        if(validarDatos()){
            DatosCrearCompetenciaPaso2DTO datosPaso2= crearDtoPaso2();
            gestorCompetencia.crearCompetencia(datosCrearCompetenciaDtoAnterior, datosPaso2);
            mostrarPopUp("La competencia ha sido creada exitosamente.", "exito");
            myController.setScreen(Main.vistaMisCompetenciasId,this);
        }
    }

    private boolean validarDatos() {
        boolean error = true;
        if(!ptsEmpateSpinner.isDisabled() && !ptsGanadosSpinner.isDisabled()){
            int ptsPartidoGanado = (Integer) ptsGanadosSpinner.getValue();
            int ptsPartidoEmpatado = (Integer) ptsEmpateSpinner.getValue();
            int ptsPorPresentarse = (Integer) ptsPorPresentarseSpinner.getValue();
            if(ptsPartidoGanado < ptsPartidoEmpatado){
                errorPuntuacion2.setText("Puntos por empate deben ser menor igual que puntos por victoria.");
                errorPuntuacion2.setVisible(true);
                error=false;
            } else{
                errorPuntuacion2.setVisible(false);
            }
        }
        if(!ptsGanadosSpinner.isDisabled() && !ptsPorPresentarseSpinner.isDisabled()){
            int ptsPartidoGanado = (Integer) ptsGanadosSpinner.getValue();
            int ptsPorPresentarse = (Integer) ptsPorPresentarseSpinner.getValue();
            if(ptsPorPresentarse >= ptsPartidoGanado){
                errorPuntuacion1.setText("Puntos por presentarse deben ser menor que puntos por victoria.");
                errorPuntuacion1.setVisible(true);
                error=false;
            } else{
                errorPuntuacion1.setVisible(false);
            }
        }
        return error;
    }

    private DatosCrearCompetenciaPaso2DTO crearDtoPaso2() {
        DatosCrearCompetenciaPaso2DTO datosCompPaso2= new DatosCrearCompetenciaPaso2DTO();
        if(datosCrearCompetenciaDtoAnterior.getModalidad().equals(Modalidad.LIGA)){
            datosCompPaso2.setEsLiga(true);
            datosCompPaso2.setPuntosPorPartidoGanado((Integer)ptsGanadosSpinner.getValue());
            datosCompPaso2.setPuntosPorPresentarse((Integer)ptsPorPresentarseSpinner.getValue());
            if(!ptsEmpateSpinner.isDisabled()){
                datosCompPaso2.setAceptaEmpates(true);
                int ptsEmpate = (Integer) ptsEmpateSpinner.getValue();
                datosCompPaso2.setPuntosPorPartidoEmpatado(ptsEmpate);
            }
            if(!tantosOtorgadosSpinner.isDisabled()){
                datosCompPaso2.setOtorgaTantosPorNoPresentarse(true);
                int tantosNoPresentarse = (Integer) tantosOtorgadosSpinner.getValue();
                datosCompPaso2.setTantosEnCasoDeNoPresentarseOponente(tantosNoPresentarse);
            }
        }
        else{
            datosCompPaso2.setEsLiga(false);
            datosCompPaso2.setAceptaEmpates(false);
            datosCompPaso2.setOtorgaTantosPorNoPresentarse(false);
        }
        datosCompPaso2.setDisponibilidades(tablaDisponibilidad.getItems());

        return datosCompPaso2;
    }

}