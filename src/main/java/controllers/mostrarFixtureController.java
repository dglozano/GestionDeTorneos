package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.ResultadoCell;
import dtos.FechaDTO;
import dtos.PartidoDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.*;
import services.GestorCompetencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mostrarFixtureController extends ControlledScreen {

    private int idCompetencia;
    private int idPartidoClickeado;
    private int fechaActual;
    private Competencia competencia;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private Stage modal;
    private Parent parent;
    private Fixture fixture;

    @FXML private Text title;
    @FXML private TabPane fechas;

    @Override
    public void inicializar(){
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
        title.setText(competencia.getNombre());
        fechaActual = gestorCompetencia.buscarFechaActual(competencia);
        generarTabs();
    }

    @Override
    public Object mensajeControladorAnterior() {
        return idCompetencia;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId, this);
    }

    public void generarTabs(){
        List<FechaDTO> listaFechasDTO = gestorCompetencia.mostrarFixture(idCompetencia);
        int cantFechas = listaFechasDTO.size();
        fechas.getTabs().clear();

        for(int i = 0; i<cantFechas; i++){
            Tab tab = new Tab();
            tab.setText("Fecha " + listaFechasDTO.get(i).getNumeroFecha());

            TableView tabla = new TableView();
            tabla.setPrefHeight(318.0);
            tabla.setPrefWidth(898.0);

            TableColumn localColumn = new TableColumn("Local");
            TableColumn resultadoColumn = new TableColumn("Resultado");
            if(competencia.getSistemaPuntuacion().equals(SistemaPuntuacion.RESULTADO_FINAL)) resultadoColumn.setText("Ganador");
            TableColumn visitanteColumn = new TableColumn("Visitante");
            TableColumn accionesColumn = new TableColumn("Acciones");
            localColumn.setPrefWidth(259);
            visitanteColumn.setPrefWidth(259);
            resultadoColumn.setPrefWidth(259);
            accionesColumn.setPrefWidth(119);

            localColumn.setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("participanteLocal"));
            resultadoColumn.setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("resultado"));
            visitanteColumn.setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("participanteVisitante"));
            tabla.getColumns().clear();
            tabla.getColumns().addAll(localColumn, resultadoColumn, visitanteColumn, accionesColumn);
            agregarBotonesEnTabla(accionesColumn);

            tabla.getItems().clear();
            List<PartidoDTO> partidoDTOs = listaFechasDTO.get(i).getPartidosDTO();
            tabla.getItems().setAll(partidoDTOs);

            tab.setContent(tabla);
            fechas.getTabs().add(tab);
        }
        int fechaMostrada = 0;
        boolean estaFinalizada = gestorCompetencia.getCompetenciaDTO(competencia.getId()).getEstado().equals(Estado.FINALIZADA.getEstadoString());
        if (myController.getControladorAnterior().getClass().toString().contains("popup")){
            fechaMostrada = gestorCompetencia.buscarFechaPartido(competencia, idPartidoClickeado) == -1 ? fechaActual : gestorCompetencia.buscarFechaPartido(competencia, idPartidoClickeado);
        } else{
            if (!estaFinalizada) {
                fechaMostrada = gestorCompetencia.buscarFechaActual(competencia);;
            } else{
                fechaMostrada = listaFechasDTO.size()-1;
            }
        }
        fechas.getSelectionModel().select(fechaMostrada);
    }

    public void setIdPartidoClickeado(int id){
        this.idPartidoClickeado = id;
    }

    public String getSistemaCompetencia(){
        return this.competencia.getSistemaPuntuacion().getPuntuacionString();
    }

    private void agregarBotonesEnTabla(TableColumn accionesColumn){
        // Seteamos una fila con valor booleano cosa que se muestre el botón para filas no vacías
        accionesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PartidoDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        mostrarFixtureController controller = this;
        accionesColumn.setCellFactory(new Callback<TableColumn<PartidoDTO, Boolean>, TableCell<PartidoDTO, Boolean>>() {
            @Override
            public TableCell<PartidoDTO, Boolean> call(TableColumn<PartidoDTO, Boolean> personBooleanTableColumn) {
                return new ResultadoCell(controller);
            }
        });
    }

    public void mostrarPopUpResultado(String tipo){
        boolean habilitado = gestorCompetencia.partidoHabilitado(competencia, idPartidoClickeado);
        if (habilitado){
            mostrarPopUpResultadoValido(tipo);
        } else{
            mostrarPopUp("Debe terminar de cargar los resultados de las fechas anteriores.", "error");
        }
    }

    private void mostrarPopUpResultadoValido(String tipo){
        String recurso;
        switch(tipo){
            case "Sets":
                recurso = "fxml/popupGestionarResultadoS.fxml";
                break;
            case "Puntos":
                recurso = "fxml/popupGestionarResultadoP.fxml";
                break;
            case "Resultado Final":
                recurso = "fxml/popupGestionarResultadoR.fxml";
                break;
            default:
                recurso = "fxml/popupError.fxml";
                break;
        }
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(recurso));
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            ControlledScreen myScreenController = ((ControlledScreen) loader.getController());
            myController.setControladorAnterior(this);
            myScreenController.setScreenParent(myController);
            myScreenController.inicializar(idPartidoClickeado+"");
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
}
