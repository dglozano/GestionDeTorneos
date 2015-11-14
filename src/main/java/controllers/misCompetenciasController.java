package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import controllers.general.VerCompetenciaCell;
import dtos.CompetenciaDTO;
import dtos.FiltrosCompetenciaDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Estado;
import models.Modalidad;
import services.GestorCompetencia;
import services.GestorDeporte;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class misCompetenciasController implements ControlledScreen {

    private PrincipalController myController;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;

    private static final int MAX_TEXT_FIELD = 254;
    private int idCompetenciaClickeada;

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
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        nombreCompetenciaTextField.clear();
        if (modalidadToggleGroup.getSelectedToggle() != null) modalidadToggleGroup.getSelectedToggle().setSelected(false);
        nombreCompetenciaTextField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if (nombreCompetenciaTextField.getText().length() >= MAX_TEXT_FIELD) {
                            nombreCompetenciaTextField.setText(nombreCompetenciaTextField.getText().substring(0, MAX_TEXT_FIELD));
                        }
                    }
        });
        inicializarDeportes();
        inicializarEstados();
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();
        tNombre.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("nombre"));
        tDeporte.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("deporte"));
        tEstado.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("estado"));
        tModalidad.setCellValueFactory(new PropertyValueFactory<CompetenciaDTO, String>("modalidad"));
        Collections.sort(listaCompetencias, new CompetenciaDTOComparator<CompetenciaDTO>());
        setearFilas(listaCompetencias);
        nombreCompetenciaTextField.requestFocus();
    }
    public void inicializar(String mensaje) {inicializar();};


    private void inicializarDeportes() {
        deportesComboBox.getItems().removeAll(deportesComboBox.getItems());
        List<String> listaDeportes = gestorDeporte.listarDeportes();
        Collections.sort(listaDeportes);
        deportesComboBox.getItems().add("Todos");
        for(String deporte: listaDeportes){
            deporte = Character.toUpperCase(deporte.charAt(0)) + deporte.substring(1).toLowerCase();
            deportesComboBox.getItems().add(deporte);
        }
        deportesComboBox.setValue("Todos");
    }

    private void inicializarEstados(){
        estadosComboBox.getItems().removeAll(estadosComboBox.getItems());
        estadosComboBox.getItems().add("Todos");
        estadosComboBox.getItems().add("Creada");
        estadosComboBox.getItems().add("Planificada");
        estadosComboBox.getItems().add("En disputa");
        estadosComboBox.getItems().add("Finalizada");
        estadosComboBox.getItems().add("Eliminada");
        estadosComboBox.setValue("Todos");
    }

    private void setearFilas(List<CompetenciaDTO> listaCompetencias){
        agregarBotonesEnTabla();
        tabla.getItems().removeAll(tabla.getItems());
        tabla.getItems().setAll(listaCompetencias);
    }

    private void agregarBotonesEnTabla(){
        // Seteamos una fila con valor booleano cosa que se muestre el botón para filas no vacías
        tAcciones.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CompetenciaDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<CompetenciaDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });

        // Creamos una nueva factory de cell con un botón de Ver competencia
        misCompetenciasController controller = this;
        tAcciones.setCellFactory(new Callback<TableColumn<CompetenciaDTO, Boolean>, TableCell<CompetenciaDTO, Boolean>>() {
            @Override
            public TableCell<CompetenciaDTO, Boolean> call(TableColumn<CompetenciaDTO, Boolean> personBooleanTableColumn) {
                return new VerCompetenciaCell(controller);
            }
        });
    }

    public void irCrearCompetencia(ActionEvent actionEvent) {
        myController.setScreen(Main.vistaCrearCompetenciaPasoUnoId, this);
    }

    public void filtrarCompetencias(ActionEvent actionEvent){
        FiltrosCompetenciaDTO filtrosCompetencia= new FiltrosCompetenciaDTO();

        String nombre = nombreCompetenciaTextField.getText().toUpperCase();
        String deporte = deportesComboBox.getValue();
        String estado = estadosComboBox.getValue();

        setearNombre(filtrosCompetencia, nombre);
        setearDeporte(filtrosCompetencia, deporte);
        setearEstado(filtrosCompetencia, estado);
        if(modalidadToggleGroup.getSelectedToggle() != null) {
            RadioButton modalidadRadioButton = (RadioButton) modalidadToggleGroup.getSelectedToggle();
            String modalidad = modalidadRadioButton.getText();
            setearModalidad(filtrosCompetencia, modalidad);
        }
        else{
            filtrosCompetencia.setFiltroModalidadActivo(false);
        }
        if(!filtrosCompetencia.filtrosInactivos()){
            List<CompetenciaDTO> listaCompetenciasFiltradas = gestorCompetencia.filtrarMisCompetencias(filtrosCompetencia);
            setearFilas(listaCompetenciasFiltradas);
        }
    }

    public void limpiarFiltros(ActionEvent actionEvent){
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();
        Collections.sort(listaCompetencias, new CompetenciaDTOComparator<CompetenciaDTO>());
        setearFilas(listaCompetencias);
        estadosComboBox.setValue("Todos");
        deportesComboBox.setValue("Todos");
        nombreCompetenciaTextField.setText("");
        if(modalidadToggleGroup.getSelectedToggle() != null){
            modalidadToggleGroup.getSelectedToggle().setSelected(false);
        }
    }

    private void setearModalidad(FiltrosCompetenciaDTO filtrosCompetencia, String modalidadString) {
        Modalidad modalidad = gestorCompetencia.asociarModalidad(modalidadString);
        filtrosCompetencia.setModalidad(modalidad);
        filtrosCompetencia.setFiltroModalidadActivo(true);
    }

    private void setearEstado(FiltrosCompetenciaDTO filtrosCompetencia, String estadoString) {
        if(!estadoString.equals("Todos")){
            Estado estado = gestorCompetencia.asociarEstado(estadoString);
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

    public void setIdCompetenciaClickeada(int id){
        this.idCompetenciaClickeada = id;
    }

    public Object mensajeControladorAnterior(){
        return idCompetenciaClickeada;
    }

    public PrincipalController getMyController(){
        return myController;
    }

    class CompetenciaDTOComparator<T> implements  Comparator<T> {
        @Override
        public int compare(T a, T b) {
            return ((CompetenciaDTO)a).getNombre().compareTo(((CompetenciaDTO)b).getNombre());
        }
    }
}
