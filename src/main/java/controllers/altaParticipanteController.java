package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.ParticipanteDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Competencia;
import services.GestorCompetencia;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

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
    private static final int MAX_TEXT_FIELD = 255;

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

    public void inicializar(){
        buscarCompetencia();
        limpiarCampos();
        agregarLengthListener();
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

    private void agregarLengthListener() {
        nombreParticipanteTextField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (nombreParticipanteTextField.getText().length() >= MAX_TEXT_FIELD) {
                    nombreParticipanteTextField.setText(nombreParticipanteTextField.getText().substring(0, MAX_TEXT_FIELD));
                }
            }
        });
        emailParticipanteTextField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (emailParticipanteTextField.getText().length() >= MAX_TEXT_FIELD) {
                    emailParticipanteTextField.setText(emailParticipanteTextField.getText().substring(0, MAX_TEXT_FIELD));
                }
            }
        });
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

        ParticipanteDTO participanteDTO = new ParticipanteDTO(nombreParticipante,emailParticipante);
        participanteDTO.setTieneImagen(false);

        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(fotoImageView.getImage(), null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", outputStream);
            byte[] imagenParticipante = outputStream.toByteArray();
            participanteDTO.setImagenParticipante(imagenParticipante);
            participanteDTO.setTieneImagen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void subirImagen(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().addAll(extFilterJPG);

        File file = fileChooser.showOpenDialog(null);
        byte[] imagenBytes = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imagenBytes);
            fileInputStream.close();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(imagenBytes);
            BufferedImage bufferedImagen = ImageIO.read(inputStream);
            Image imagen = SwingFXUtils.toFXImage(bufferedImagen, null);
            fotoImageView.setImage(imagen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
