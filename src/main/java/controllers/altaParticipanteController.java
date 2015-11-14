package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.CrearParticipanteDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Competencia;
import services.GestorCompetencia;

import java.io.IOException;

/**
 * Created by DIego on 11/11/2015..
 */
public class altaParticipanteController implements ControlledScreen {

    private int idCompetencia;
    private Competencia competencia;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private PrincipalController myController;
    private Stage modal;
    private Parent parent;


    private final String regexEmail = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@" +
            "([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|" +
            "mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]" +
            "{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    private final String regexNombre = "^[\\w]+( [\\w]+)*$";

    @FXML private TextField nombreParticipanteTextField;
    @FXML private TextField emailParticipanteTextField;
    @FXML private Button cargarButton;
    @FXML private ImageView fotoImageView;
    @FXML private Text title;
    @FXML private Label errorNombre;
    @FXML private Label errorEmail;
    @FXML private Button okButton;


    public void inicializar(){
        buscarCompetencia();
        limpiarCampos();
        //TODO 03: FOTO
    }
    public void inicializar(String mensaje) {inicializar();};


    private void limpiarCampos() {
        nombreParticipanteTextField.clear();
        emailParticipanteTextField.clear();
        Image image = new Image("image/profile.jpg");
        fotoImageView.setImage(image);
        errorEmail.setVisible(false);
        errorNombre.setVisible(false);
    }

    private void buscarCompetencia() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
        title.setText(competencia.getNombre());
    }

    public void darDeAlta(ActionEvent action){
        if(validarDatos()){
            boolean teniaFixture = competencia.getFixture()!=null;
            crearParticipante();
            if(teniaFixture){
                mostrarPopUp("El participante se ha agregado exitosamente. \nSe ha eliminado el Fixture.","exito");
            }
            else{
                mostrarPopUp("El participante se ha agregado exitosamente.","exito");
            }
            myController.setScreen(Main.vistaListarParticipantesId);
        }
    }

    private void crearParticipante() {
        String nombreParticipante = nombreParticipanteTextField.getText();
        String emailParticipante = emailParticipanteTextField.getText();
        CrearParticipanteDTO participanteDTO = new CrearParticipanteDTO(nombreParticipante,emailParticipante);
        // TODO 03: si tiene foto setearsela
        participanteDTO.setTieneImagen(false);
        gestorCompetencia.agregarParticipante(participanteDTO,idCompetencia);
    }

    private boolean validarDatos() {
        String nombreParticipante = nombreParticipanteTextField.getText();
        String emailParticipante = emailParticipanteTextField.getText();
        boolean nombreOK = validarNombre(nombreParticipante);
        boolean emailOK = validarEmail(emailParticipante);

        return nombreOK && emailOK;

    }

    private boolean validarNombre(String nombreParticipante) {
        boolean nombreValido = nombreParticipante.matches(regexNombre);
        if(nombreParticipante.isEmpty()){
            errorNombre.setText("Este campo es obligatorio");
            errorNombre.setVisible(true);
            return false;
        }
        else if(!nombreValido){
            errorNombre.setText("El nombre ingresado no es valido");
            errorNombre.setVisible(true);
            return false;
        }
        else{
            boolean nombreExistente = gestorCompetencia.existeNombreParticipante(idCompetencia,nombreParticipante);
            if(nombreExistente){
                errorNombre.setText("El nombre ingresado ya existe");
                errorNombre.setVisible(true);
                return false;
            }
            else{
                errorNombre.setVisible(false);
                return true;
            }
        }
    }

    private boolean validarEmail(String emailParticipante) {
        boolean emailValido = emailParticipante.matches(regexEmail);
        if(emailParticipante.isEmpty()){
            errorEmail.setText("Este campo es obligatorio");
            errorEmail.setVisible(true);
            return false;
        }
        else if(!emailValido){
            errorEmail.setText("El email ingresado no es valido");
            errorEmail.setVisible(true);
            return false;
        }
        else{
            boolean emailExistente = gestorCompetencia.existeEmailParticipante(idCompetencia, emailParticipante);
            if(emailExistente){
                errorEmail.setText("El email ingresado ya existe");
                errorEmail.setVisible(true);
                return false;
            }
            else{
                errorEmail.setVisible(false);
                return true;
            }
        }
    }

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaListarParticipantesId);
    }

    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

    private void mostrarPopUp(){
        mostrarPopUp("","");
    }

    private void mostrarPopUp(String mensaje, String tipo){
        String recurso;
        switch(tipo){
            case "error":
                recurso = "fxml/popupError.fxml";
                break;
            case "exito":
                recurso = "fxml/popupExito.fxml";
                break;
            default:
                recurso = "fxml/popupEnDesarrollo.fxml";
                break;
        }
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(recurso));
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            ControlledScreen myScreenControler = ((ControlledScreen) loader.getController());
            myScreenControler.setScreenParent(myController);
            myScreenControler.inicializar(mensaje);
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


    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }
}
