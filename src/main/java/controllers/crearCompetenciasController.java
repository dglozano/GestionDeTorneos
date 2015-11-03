package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;
import services.GestorCompetencia;
import services.GestorDeporte;
import services.GestorLugarRealizacion;

import java.io.IOException;
import java.util.List;

public class crearCompetenciasController implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;
    private GestorLugarRealizacion gestorLugarRealizacion;
    //private GestorUsuario gestorUsuario;
    //private GestorLugar

    @FXML private TextField nombreCompetenciaTextField;

    @FXML private Label errorPuntuacionLabel;
    @FXML private Label errorNombreLabel;
    @FXML private Label errorLugaresLabel;

    @FXML private Label cantidadSetsLabel;
    @FXML private ToggleGroup puntuacionToggleGroup;
    @FXML private ComboBox<String> deportesComboBox;
    @FXML private ComboBox<String> setsComboBox;
    @FXML private ComboBox<String> modalidadComboBox;
    @FXML private CheckComboBox<String> lugaresComboBox;

    @FXML private Button okButton;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        gestorCompetencia = new GestorCompetencia();
        gestorDeporte = new GestorDeporte();
        gestorLugarRealizacion = new GestorLugarRealizacion();
        inicializarModalidades();
        inicializarDeportes();
       // inicializarLugares(deportesComboBox.getValue().toUpperCase());
        inicializarSets();
        nombreCompetenciaTextField.requestFocus();

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

    /*private void inicializarLugares(String deporteSeleccionado) {
        lugaresComboBox.getCheckModel().clearChecks();
        lugaresComboBox.getItems().clear();
        List<String> deportesString = gestorLugarRealizacion.buscarLugaresDelUsuario(deporteSeleccionado);
        final ObservableList<String> deportesObservable = FXCollections.observableArrayList();
        for(String depString: deportesString)
            deportesObservable.add(depString);
        lugaresComboBox.getItems().addAll(deportesObservable);
    }*/

    private void inicializarDeportes() {
        deportesComboBox.getItems().removeAll(deportesComboBox.getItems());
        List<String> listaDeportes = gestorDeporte.listarDeportes();
        for(String deporte: listaDeportes){
            deporte = Character.toUpperCase(deporte.charAt(0)) + deporte.substring(1).toLowerCase();
            deportesComboBox.getItems().add(deporte);
        }
        deportesComboBox.setValue(deportesComboBox.getItems().get(0));
    }

    private void inicializarModalidades() {
        modalidadComboBox.getItems().removeAll(modalidadComboBox.getItems());
        modalidadComboBox.getItems().add("Liga");
        modalidadComboBox.getItems().add("Eliminacion simple");
        modalidadComboBox.getItems().add("Eliminacion doble");
        modalidadComboBox.setValue("Liga");
    }

    public boolean validacionesOk(){
        boolean nombreOk = validarNombreCompetencia();
        boolean puntuacionOk = validarPuntuacionSeleccionada();

        return nombreOk && puntuacionOk;
    }

    private boolean validarPuntuacionSeleccionada() {
        if (puntuacionToggleGroup.getSelectedToggle() == null){
            errorPuntuacionLabel.setText("Debe completar este campo para continuar.");
            errorPuntuacionLabel.setVisible(true);
            //errorLugaresLabel.setVisible(true);
            //errorNombreLabel.setVisible(true);
            return false;
        } else{
            errorPuntuacionLabel.setVisible(false);
            return true;
        }
    }

    private boolean validarNombreCompetencia() {
        String nombreCompetencia = nombreCompetenciaTextField.getText().toUpperCase();
        boolean caracteresValidos = validarCaracteres(nombreCompetencia);
        if(nombreCompetencia.isEmpty()){
            System.out.println("Este campo es obligatorio");
            return false;
        }
        else if(!caracteresValidos){
            //TODO 03: Crear Labels
            System.out.println("Se permiten solo numeros y letras");
            return false;
        }
        else{
            boolean nombreExistente = gestorCompetencia.existeNombre(nombreCompetencia);
            if(nombreExistente){
                System.out.println("El nombre de la competencia ya existe");
                return false;
            }
        }
        return true;
    }

    private boolean validarCaracteres(String nombre) {
        for(char caracter: nombre.toCharArray()){
            if(!Character.isLetterOrDigit(caracter) && !Character.isSpaceChar(caracter))
                return false;
        }
        return true;
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

    public void irMisCompetencias(ActionEvent actionEvent) {
        myController.setScreen(Main.vista1ID);
    }

    public void continuar(ActionEvent actionEvent) {
        if(validacionesOk()){
            myController.setScreen(Main.vista3ID);
            //mostrarPopupExito();
        }
    }

    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
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
        String deporteSeleccionado = ((ComboBox<String>)actionEvent.getSource()).getValue().toString().toUpperCase();
      //  inicializarLugares(deporteSeleccionado);
    }


}
