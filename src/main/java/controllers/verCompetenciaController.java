package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.CompetenciaDTO;
import exceptions.FixtureException.DisponibilidadesInsuficientesFixtureException;
import exceptions.FixtureException.EstadoErrorFixtureException;
import exceptions.FixtureException.PocosParticipantesFixtureException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Estado;
import models.Modalidad;
import services.GestorCompetencia;

import java.io.IOException;


/**
 * Created by Kevin on 09/11/2015.
 */
public class verCompetenciaController implements ControlledScreen{

    private PrincipalController myController;
    private GestorCompetencia gestorCompetencia;
    private int idCompetencia;
    private CompetenciaDTO competenciaDTO;
    private Stage modal;
    private Parent parent;

    @FXML private TextField modalidadTextField;
    @FXML private TextField deporteTextField;
    @FXML private TextField estadoTextField;
    @FXML private TextField proximoEncuentroTextField;
    @FXML private Text title;
    @FXML private Button okButton;
    @FXML private Label detailsLabel;


    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }


   public void inicializar() {
        idCompetencia= (Integer)myController.getControladorAnterior().mensajeControladorAnterior();
        gestorCompetencia = new GestorCompetencia();
        competenciaDTO = gestorCompetencia.getCompetencia(idCompetencia);
        title.setText(competenciaDTO.getNombre());
        deporteTextField.setText(competenciaDTO.getDeporte());
        modalidadTextField.setText(competenciaDTO.getModalidad());
        estadoTextField.setText(competenciaDTO.getEstado());
        /* TODO 02: setear proximo encuentro cuando funcione fixture */
    }


    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaMisCompetenciasId);
    }

    public void verTablaPosiciones(ActionEvent actionEvent){
        boolean esLiga = competenciaDTO.getModalidad().equals(Modalidad.LIGA.getModalidadString());
        boolean estaEnDisputa = competenciaDTO.getEstado().equals(Estado.EN_DISPUTA.getEstadoString());
        boolean estaFinalizada = competenciaDTO.getEstado().equals(Estado.FINALIZADA.getEstadoString());
        if(esLiga && (estaEnDisputa || estaFinalizada )){
            myController.setScreen(Main.vistaTablaPosicionesId);
        }
        else{
                mostrarPopUp("fxml/popupErrorTablaPosiciones.fxml");
        }
    }

    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }

    public void irListarParticipantes(ActionEvent actionEvent){
        myController.setScreen(Main.vistaListarParticipantesId);
    }

    public void irDarDeBaja(ActionEvent actionEvent) {
        mostrarPopUp("fxml/popupEnDesarrollo.fxml");
    }

    public void irModificarCompetencia(ActionEvent actionEvent) {
        mostrarPopUp("fxml/popupEnDesarrollo.fxml");
    }

    public void irGenerarFixture(ActionEvent actionEvent){
        try{
            gestorCompetencia.generarFixture(idCompetencia);
            mostrarPopUp("fxml/popupFixtureCreado.fxml");
            estadoTextField.setText(Estado.PLANIFICADA.getEstadoString());
        }
        catch(EstadoErrorFixtureException e){
            mostrarPopUp("fxml/popupErrorFixtureEstado.fxml");
        }
        catch(PocosParticipantesFixtureException e){
            mostrarPopUp("fxml/popupErrorFixtureParticipantes.fxml");
        }
        catch (DisponibilidadesInsuficientesFixtureException e){
            mostrarPopUp("fxml/popupErrorFixtureDisponibilidades.fxml");
        }
    }

    private void mostrarPopUp(String archivoFXML){
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(archivoFXML));
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

}
