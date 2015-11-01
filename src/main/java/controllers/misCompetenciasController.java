package controllers;

import app.Main;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.FiltrosCompetenciaDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import dtos.CompetenciaDTO;
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

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        this.gestorCompetencia = new GestorCompetencia();
        this.gestorDeporte = new GestorDeporte();
        deportesComboBox.getItems().removeAll(deportesComboBox.getItems());
        estadosComboBox.getItems().removeAll(estadosComboBox.getItems());
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();
        /* TODO 02: Aniadir a tabla*/
        cargarDeportes();
        cargarEstados();
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
        RadioButton modalidadRadioButton= (RadioButton) modalidadToggleGroup.getSelectedToggle();
        String modalidad = modalidadRadioButton.getText();
        setearNombre(filtrosCompetencia, nombre);
        setearDeporte(filtrosCompetencia, deporte);
        setearEstado(filtrosCompetencia, estado);
        setearModalidad(filtrosCompetencia, modalidad);
        List<CompetenciaDTO> listaCompetenciasFiltradas = gestorCompetencia.filtrarMisCompetencias(filtrosCompetencia);
         //TODO 02: Descomentar cuando pueda cargar datos en la tabla de la vista
    }

    public void limpiarFiltros(ActionEvent actionEvent){
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();
        /* TODO 02: Aniadir a tabla*/
        estadosComboBox.setValue("Todos");
        deportesComboBox.setValue("Todos");
        nombreCompetenciaTextField.setText("");
        modalidadToggleGroup.getSelectedToggle().setSelected(false);
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
