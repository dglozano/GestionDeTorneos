package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import dtos.DatosCrearCompetenciaDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Modalidad;
import models.SistemaPuntuacion;
import org.controlsfx.control.CheckComboBox;
import services.GestorCompetencia;
import services.GestorDeporte;
import services.GestorLugarRealizacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class crearCompetenciasController extends ControlledScreen {

    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;
    private GestorLugarRealizacion gestorLugarRealizacion;
    private DatosCrearCompetenciaDTO datosCrearCompetenciaDto;
     private final String regexNombreCompetencia = "^[\\w]+( [\\w]+)*$";

    private static final int MAX_TEXT_FIELD = 254;
    private static final int MAX_TEXT_AREA = 65000;

    @FXML private TextField nombreCompetenciaTextField;

    @FXML private Label errorPuntuacionLabel;
    @FXML private Label errorNombreLabel;
    @FXML private Label errorLugaresLabel;

    @FXML private Label cantidadSetsLabel;
    @FXML private Label lugaresLabel;
    @FXML private ToggleGroup puntuacionToggleGroup;
    @FXML private ComboBox<String> deportesComboBox;
    @FXML private ComboBox<String> setsComboBox;
    @FXML private ComboBox<String> modalidadComboBox;
    @FXML private CheckComboBox<String> lugaresComboBox;
    @FXML private TextArea reglamentoTextArea;

    @Override
    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        gestorLugarRealizacion = new GestorLugarRealizacion();
        errorLugaresLabel.setVisible(false);
        errorNombreLabel.setVisible(false);
        errorPuntuacionLabel.setVisible(false);
        reglamentoTextArea.clear();
        nombreCompetenciaTextField.clear();
        nombreCompetenciaTextField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (nombreCompetenciaTextField.getText().length() >= MAX_TEXT_FIELD) {
                        nombreCompetenciaTextField.setText(nombreCompetenciaTextField.getText().substring(0, MAX_TEXT_FIELD));
                    }
                }
        });
        reglamentoTextArea.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (reglamentoTextArea.getText().length() >= MAX_TEXT_AREA) {
                        reglamentoTextArea.setText(reglamentoTextArea.getText().substring(0, MAX_TEXT_AREA));
                    }
                }
        });
        inicializarModalidades();
        inicializarDeportes();
        inicializarSets();
        if(puntuacionToggleGroup.getSelectedToggle() != null){
            puntuacionToggleGroup.getSelectedToggle().setSelected(false);
            cantidadSetsLabel.setDisable(true);
            setsComboBox.setDisable(true);
        }
        String nombreControladorAnterior = myController.getControladorAnterior().getClass().getSimpleName();
        boolean volvioDelPaso2 = nombreControladorAnterior.equals("crearCompetencias2Controller");
        if(volvioDelPaso2){
            datosCrearCompetenciaDto = (DatosCrearCompetenciaDTO) myController.getControladorAnterior().mensajeControladorAnterior();
            rellenarConDatosCargados();
        }
        else{
            inicializarLugares(deportesComboBox.getValue().toUpperCase());
        }
        nombreCompetenciaTextField.requestFocus();

    }

    private void rellenarConDatosCargados() {
        nombreCompetenciaTextField.setText(datosCrearCompetenciaDto.getCompetencia());
        modalidadComboBox.setValue(datosCrearCompetenciaDto.getModalidad().getModalidadString());
        Toggle puntuacion = null;
        for(Toggle toggle: puntuacionToggleGroup.getToggles()){
            if(((RadioButton)toggle).getText().equals(datosCrearCompetenciaDto.getPuntuacion().getPuntuacionString())){
                puntuacion = toggle;
            }
        }
        puntuacionToggleGroup.selectToggle(puntuacion);
        if(datosCrearCompetenciaDto.isTieneSets()){
            setsComboBox.setValue(datosCrearCompetenciaDto.getSets()+"");
            cantidadSetsLabel.setDisable(false);
            setsComboBox.setDisable(false);
        }
        if(datosCrearCompetenciaDto.isTieneReglamento()){
            reglamentoTextArea.setText(datosCrearCompetenciaDto.getReglamento());
        }

        String deporte = datosCrearCompetenciaDto.getDeporte();
        String deporteParseado= Character.toUpperCase(deporte.charAt(0)) + deporte.substring(1).toLowerCase();
        deportesComboBox.setValue(deporteParseado);
        inicializarLugares(deportesComboBox.getValue().toUpperCase());
        for(String lugarNombre: datosCrearCompetenciaDto.getListaLugaresNombres()){
            for(int i=0; i<lugaresComboBox.getItems().size(); i++){
                String nombreLugar = lugaresComboBox.getItems().get(i);
                if(nombreLugar.equals(lugarNombre))
                    lugaresComboBox.getCheckModel().check(i);
            }
        }
    }

    private void inicializarSets() {
        setsComboBox.getItems().removeAll(setsComboBox.getItems());
        setsComboBox.getItems().add("1");
        setsComboBox.getItems().add("3");
        setsComboBox.getItems().add("5");
        setsComboBox.getItems().add("7");
        setsComboBox.getItems().add("9");
        setsComboBox.setValue("1");
    }

    private void inicializarLugares(String deporteSeleccionado) {
        lugaresComboBox.getCheckModel().clearChecks();
        lugaresComboBox.getItems().clear();
        List<String> lugaresNombres = gestorLugarRealizacion.buscarLugaresDelUsuario(deporteSeleccionado);
        Collections.sort(lugaresNombres);
        final ObservableList<String> lugaresObservable = FXCollections.observableArrayList();
        for(String lugarNombre: lugaresNombres){
            lugarNombre = (Character.isLetter(lugarNombre.charAt(0))) ?
                    Character.toUpperCase(lugarNombre.charAt(0)) + lugarNombre.substring(1).toLowerCase()
                    : lugarNombre.toLowerCase();
            lugaresObservable.add(lugarNombre);
        }
        if(lugaresNombres.isEmpty()){
            errorLugaresLabel.setText("No existen lugares para el deporte seleccionado.");
            errorLugaresLabel.setVisible(true);
            lugaresComboBox.setDisable(true);
            lugaresLabel.setDisable(true);
        }
        else{
            errorLugaresLabel.setVisible(false);
            lugaresComboBox.setDisable(false);
            lugaresLabel.setDisable(false);
            lugaresComboBox.getItems().addAll(lugaresObservable);
        }
    }

    private void inicializarDeportes() {
        deportesComboBox.setValue(null);
        deportesComboBox.getItems().removeAll(deportesComboBox.getItems());
        List<String> listaDeportes = gestorDeporte.listarDeportes();
        Collections.sort(listaDeportes);
        for(String deporte: listaDeportes){
            deporte = (Character.isLetter(deporte.charAt(0))) ?
                    Character.toUpperCase(deporte.charAt(0)) + deporte.substring(1).toLowerCase()
                    : deporte.toLowerCase();
            deportesComboBox.getItems().add(deporte);
        }
        deportesComboBox.setValue(deportesComboBox.getItems().get(0));
    }

    private void inicializarModalidades() {
        modalidadComboBox.getItems().removeAll(modalidadComboBox.getItems());
        modalidadComboBox.getItems().add("Liga");
        modalidadComboBox.getItems().add("Eliminatoria Simple");
        modalidadComboBox.getItems().add("Eliminatoria Doble");
        modalidadComboBox.setValue("Liga");
    }

    public boolean validacionesOk(){
        boolean nombreOk = validarNombreCompetencia();
        boolean puntuacionOk = validarPuntuacionSeleccionada();
        boolean lugaresOk = validarLugares();

        return nombreOk && puntuacionOk && lugaresOk;
    }

    private boolean validarLugares() {
        if(lugaresComboBox.getCheckModel().getCheckedItems().isEmpty()){
            errorLugaresLabel.setText("Debe elegir al menos un lugar.");
            errorLugaresLabel.setVisible(true);
            return false;
        }
        else{
            errorLugaresLabel.setVisible(false);
            return true;
        }
    }

    private boolean validarPuntuacionSeleccionada() {
        if (puntuacionToggleGroup.getSelectedToggle() == null){
            errorPuntuacionLabel.setText("Debe completar este campo para continuar.");
            errorPuntuacionLabel.setVisible(true);
            return false;
        } else{
            errorPuntuacionLabel.setVisible(false);
            return true;
        }
    }

    private boolean validarNombreCompetencia() {
        String nombreCompetencia = nombreCompetenciaTextField.getText().toUpperCase();
        boolean cadenaValida = nombreCompetencia.matches(regexNombreCompetencia);
        if(nombreCompetencia.isEmpty()){
            errorNombreLabel.setText("Este campo es obligatorio.");
            errorNombreLabel.setVisible(true);
            nombreCompetenciaTextField.requestFocus();
            return false;
        }
        else if(!cadenaValida){
            errorNombreLabel.setText("Solo se permiten letras y numeros.");
            errorNombreLabel.setVisible(true);
            nombreCompetenciaTextField.requestFocus();
            return false;
        }
        else{
            boolean nombreExistente = gestorCompetencia.existeNombre(nombreCompetencia);
            if(nombreExistente){
                errorNombreLabel.setText("El nombre de la competencia ya existe.");
                errorNombreLabel.setVisible(true);
                nombreCompetenciaTextField.requestFocus();
                return false;
            }
        }
        errorNombreLabel.setVisible(false);
        return true;
    }

    private boolean validarCaracteres(String nombre) {
        for(char caracter: nombre.toCharArray()){
            if(!Character.isLetterOrDigit(caracter) && !Character.isSpaceChar(caracter))
                return false;
        }
        return true;
    }

    public void irMisCompetencias(ActionEvent actionEvent) {
        myController.setScreen(Main.vistaMisCompetenciasId,this);
    }

    public void continuar(ActionEvent actionEvent) {
        if(validacionesOk()){
            crearDto();
            myController.setScreen(Main.vistaCrearCompetenciaPasoDosId, this);
        }
    }

    private void crearDto() {
        String nombre = nombreCompetenciaTextField.getText();
        String deporte = deportesComboBox.getValue().toString().toUpperCase();
        RadioButton puntuacionRadioButton = (RadioButton) puntuacionToggleGroup.getSelectedToggle();
        String puntuacionString = puntuacionRadioButton.getText();
        SistemaPuntuacion puntuacion = gestorCompetencia.asociarSistemaPuntuacion(puntuacionString);
        Modalidad modalidad = gestorCompetencia.asociarModalidad(modalidadComboBox.getValue().toString());
        List<String> listaLugaresNombres = new ArrayList<>();
        for(String nombreLugar: lugaresComboBox.getCheckModel().getCheckedItems()){
            listaLugaresNombres.add(nombreLugar);
        }
        this.datosCrearCompetenciaDto = new DatosCrearCompetenciaDTO(nombre,deporte,modalidad,puntuacion,listaLugaresNombres);
        if(!reglamentoTextArea.getText().isEmpty()){
            this.datosCrearCompetenciaDto.setReglamento(reglamentoTextArea.getText());
            this.datosCrearCompetenciaDto.setTieneReglamento(true);
        }
        else{
            this.datosCrearCompetenciaDto.setTieneReglamento(false);
        }
        if(puntuacion.equals(SistemaPuntuacion.SET)){
            int sets = Integer.parseInt(setsComboBox.getValue());
            this.datosCrearCompetenciaDto.setSets(sets);
            this.datosCrearCompetenciaDto.setTieneSets(true);
        }
        else{
            this.datosCrearCompetenciaDto.setTieneSets(false);
        }

    }

    public void puntuacionRadioButtonPressed(ActionEvent actionEvent){
       String source = ((RadioButton)actionEvent.getSource()).getText();
        if(source.equals("Sets")){
            setsComboBox.setDisable(false);
            cantidadSetsLabel.setDisable(false);
        }
        else{
            setsComboBox.setDisable(true);
            cantidadSetsLabel.setDisable(true);
        }
    }

    public void deporteSeleccionado(ActionEvent actionEvent){
        if(deportesComboBox.getValue() != null){
            String deporteSeleccionado = deportesComboBox.getValue().toString().toUpperCase();
            inicializarLugares(deporteSeleccionado);
        }
    }

    @Override
    public Object mensajeControladorAnterior(){
        return this.datosCrearCompetenciaDto;
    }
}
