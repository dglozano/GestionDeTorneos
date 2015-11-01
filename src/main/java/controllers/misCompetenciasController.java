package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import controllers.general.VerCompetenciaCell;
import dtos.CompetenciaDTO;
import dtos.FiltrosCompetenciaDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Estado;
import models.Modalidad;
import services.GestorCompetencia;
import services.GestorDeporte;

import java.util.List;

public class misCompetenciasController implements ControlledScreen {

    private PrincipalController myController;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;

    @FXML private TextField nombreCompetenciaTextField;
    @FXML private ToggleGroup modalidadToggleGroup;
    @FXML private ComboBox<String> deportesComboBox;
    @FXML private ComboBox<String> estadosComboBox;

    @FXML private TableView<CompetenciaDTO> tabla;
    @FXML private TableColumn<CompetenciaDTO, String> tNombre;
    @FXML private TableColumn<CompetenciaDTO, String> tDeporte;
    @FXML private TableColumn<CompetenciaDTO, String> tEstado;
    @FXML private TableColumn<CompetenciaDTO, String> tModalidad;
    @FXML private TableColumn<CompetenciaDTO, Boolean> tAcciones;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        this.gestorCompetencia = new GestorCompetencia();
        this.gestorDeporte = new GestorDeporte();
        deportesComboBox.getItems().removeAll(deportesComboBox.getItems());
        estadosComboBox.getItems().removeAll(estadosComboBox.getItems());
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();

        tNombre.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("nombre"));
        tDeporte.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("deporte"));
        tEstado.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("estado"));
        tModalidad.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("modalidad"));

        setearFilas(listaCompetencias);

        cargarDeportes();
        cargarEstados();
    }

    private void agregarBotonesEnTabla(){
        // Seteamos una fila con valor booleano cosa que se muestre el botón para filas no vacías
        tAcciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompetenciaDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<CompetenciaDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });

        // Creamos una nueva factory de cell con un botón de Ver competencia
        tAcciones.setCellFactory(new Callback<TableColumn<CompetenciaDTO, Boolean>, TableCell<CompetenciaDTO, Boolean>>() {
            @Override
            public TableCell<CompetenciaDTO, Boolean> call(TableColumn<CompetenciaDTO, Boolean> personBooleanTableColumn) {
                return new VerCompetenciaCell(tabla);
            }
        });
    }

    private void setearFilas(List<CompetenciaDTO> listaCompetencias){
        agregarBotonesEnTabla();
        tabla.getItems().removeAll(tabla.getItems());
        tabla.getItems().setAll(listaCompetencias);
    }

    private void cargarDeportes() {
        List<String> listaDeportes = gestorDeporte.listarDeportes();
        deportesComboBox.getItems().add("Todos");
        for(String deporte: listaDeportes){
            deporte = Character.toUpperCase(deporte.charAt(0)) + deporte.substring(1).toLowerCase();
            deportesComboBox.getItems().add(deporte);
        }
        deportesComboBox.setValue("Todos");
    }

    private void cargarEstados(){
        estadosComboBox.getItems().add("Todos");
        estadosComboBox.getItems().add("Creada");
        estadosComboBox.getItems().add("Planificada");
        estadosComboBox.getItems().add("En disputa");
        estadosComboBox.getItems().add("Finalizada");
        estadosComboBox.getItems().add("Eliminada");
        estadosComboBox.setValue("Todos");
    }

    public void irCrearCompetencia(ActionEvent actionEvent) {
        myController.setScreen(Main.vista2ID);
    }

    public void filtrarCompetencias(ActionEvent actionEvent){
        FiltrosCompetenciaDTO filtrosCompetencia= new FiltrosCompetenciaDTO();
        String nombre = nombreCompetenciaTextField.getText().toUpperCase();
        String deporte = deportesComboBox.getValue();
        String estado = estadosComboBox.getValue();
        if(modalidadToggleGroup.getSelectedToggle() != null) {
            RadioButton modalidadRadioButton = (RadioButton) modalidadToggleGroup.getSelectedToggle();
            String modalidad = modalidadRadioButton.getText();
            setearModalidad(filtrosCompetencia, modalidad);
        }
        setearNombre(filtrosCompetencia, nombre);
        setearDeporte(filtrosCompetencia, deporte);
        setearEstado(filtrosCompetencia, estado);

        List<CompetenciaDTO> listaCompetenciasFiltradas = gestorCompetencia.filtrarMisCompetencias(filtrosCompetencia);
        setearFilas(listaCompetenciasFiltradas);
    }

    public void limpiarFiltros(ActionEvent actionEvent){
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();
        setearFilas(listaCompetencias);
        estadosComboBox.setValue("Todos");
        deportesComboBox.setValue("Todos");
        nombreCompetenciaTextField.setText("");
        if(modalidadToggleGroup.getSelectedToggle() != null){
            modalidadToggleGroup.getSelectedToggle().setSelected(false);
        }
    }

    private void setearModalidad(FiltrosCompetenciaDTO filtrosCompetencia, String modalidadString) {
        // TODO 03: Ver como hacer para que no tenga que obligarotiamente elegir alguno de este filtro
        Modalidad modalidad = asociarModalidad(modalidadString);
        filtrosCompetencia.setModalidad(modalidad);
        filtrosCompetencia.setFiltroModalidadActivo(true);
    }

    private void setearEstado(FiltrosCompetenciaDTO filtrosCompetencia, String estadoString) {
        if(!estadoString.equals("Todos")){
            Estado estado = asociarEstado(estadoString);
            filtrosCompetencia.setEstado(estado);
            filtrosCompetencia.setFiltroEstadoActivo(true);
        }
        else{
            filtrosCompetencia.setFiltroEstadoActivo(false);
        }
    }

    private void setearDeporte(FiltrosCompetenciaDTO filtrosCompetencia, String deporte) {
        if (!deporte.equals("Todos")) {
            filtrosCompetencia.setDeporte(deporte.toUpperCase());
            filtrosCompetencia.setFiltroDeporteActivo(true);
        } else {
            filtrosCompetencia.setFiltroDeporteActivo(false);
        }
    }

    private void setearNombre(FiltrosCompetenciaDTO filtrosCompetencia, String nombre) {
        if(!nombre.isEmpty()){
            filtrosCompetencia.setNombre(nombre.toUpperCase());
            filtrosCompetencia.setFiltroNombreActivo(true);
        }
        else{
            filtrosCompetencia.setFiltroNombreActivo(false);
        }
    }

    private Modalidad asociarModalidad(String modalidadString) {
        switch(modalidadString){
            case "Liga" : return Modalidad.LIGA;
            case "Eliminatoria Simple" : return Modalidad.ELIM_SIMPLE;
            case "Eliminatoria Doble" : return Modalidad.ELIM_DOBLE;
        }
        return null;
    }

    private Estado asociarEstado(String estadoString) {
        switch(estadoString){
            case "Creada": return Estado.CREADA;
            case "En disputa": return Estado.EN_DISPUTA;
            case "Eliminada": return Estado.ELIMINADA;
            case "Planificada": return Estado.PLANIFICADA;
            case "Finalizada": return Estado.FINALIZADA;
        }
        return null;
    }

}
