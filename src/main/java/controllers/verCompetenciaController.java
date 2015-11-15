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
    public void inicializar(String mensaje) {inicializar();}

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
        if(esLiga){
            if(estaEnDisputa || estaFinalizada)
                myController.setScreen(Main.vistaTablaPosicionesId);
            else
                mostrarPopUp("La competencia no esta en Disputa o Finalizada.", "error");

        }
        else{
                mostrarPopUp("La competencia no es de la modalidad Liga", "error");
        }
    }

    public void irListarParticipantes(ActionEvent actionEvent){
        myController.setScreen(Main.vistaListarParticipantesId);
    }

    public void irMostrarFixture(ActionEvent actionEvent){
        competenciaDTO = gestorCompetencia.getCompetencia(idCompetencia);
        if(!competenciaDTO.getEstado().equals(Estado.CREADA.getEstadoString())) {
            myController.setScreen(Main.vistaMostrarFixtureId);
        }
        else{
            mostrarPopUp("El fixture aun no ha sido generado","error");
        }
    }

    public void irDarDeBaja(ActionEvent actionEvent) {
        mostrarPopUp("Esta funcionalidad esta en desarrollo","desarrollo");
    }

    public void irModificarCompetencia(ActionEvent actionEvent) {
        mostrarPopUp("Esta funcionalidad esta en desarrollo","desarrollo");
    }

    public void irGenerarFixture(ActionEvent actionEvent){
        try{
            gestorCompetencia.generarFixture(idCompetencia);
            mostrarPopUp("El fixture se ha generado exitosamente.", "exito");
            estadoTextField.setText(Estado.PLANIFICADA.getEstadoString());
        }
        catch(EstadoErrorFixtureException e){
            mostrarPopUp("La competencia ya esta en Disputa o Finalizada.", "error");
        }
        catch(PocosParticipantesFixtureException e){
            mostrarPopUp("La competencia debe tener por lo menos dos participantes.", "error");
        }
        catch (DisponibilidadesInsuficientesFixtureException e){
            mostrarPopUp("La competencia no tiene suficientes Disponibilidades asignadas.", "error");
        }
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

}
