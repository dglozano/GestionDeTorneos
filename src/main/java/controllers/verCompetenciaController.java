package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import dtos.CompetenciaDTO;
import exceptions.FixtureException.DisponibilidadesInsuficientesFixtureException;
import exceptions.FixtureException.EstadoErrorFixtureException;
import exceptions.FixtureException.PocosParticipantesFixtureException;
import exceptions.FuncionalidadEnDesarrolloException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Estado;
import models.Modalidad;
import services.GestorCompetencia;

public class verCompetenciaController extends ControlledScreen{

    private GestorCompetencia gestorCompetencia=new GestorCompetencia();;
    private int idCompetencia;
    private CompetenciaDTO competenciaDTO;

    @FXML private TextField modalidadTextField;
    @FXML private TextField deporteTextField;
    @FXML private TextField estadoTextField;
    @FXML private TextField proximoEncuentroTextField;
    @FXML private Text title;

    @Override
    public void inicializar() {
        idCompetencia= (Integer)myController.getControladorAnterior().mensajeControladorAnterior();
        cargarDatosCompetencia();
    }

    private void cargarDatosCompetencia() {
        competenciaDTO = gestorCompetencia.getCompetenciaDTO(idCompetencia);
        title.setText(competenciaDTO.getNombre());
        deporteTextField.setText(competenciaDTO.getDeporte());
        modalidadTextField.setText(competenciaDTO.getModalidad());
        estadoTextField.setText(competenciaDTO.getEstado());
        proximoEncuentroTextField.setText(competenciaDTO.getProximoEncuentro());
    }

    @Override
    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaMisCompetenciasId,this);
    }

    public void verTablaPosiciones(ActionEvent actionEvent){
        boolean esLiga = competenciaDTO.getModalidad().equals(Modalidad.LIGA.getModalidadString());
        boolean estaEnDisputa = competenciaDTO.getEstado().equals(Estado.EN_DISPUTA.getEstadoString());
        boolean estaFinalizada = competenciaDTO.getEstado().equals(Estado.FINALIZADA.getEstadoString());
        if(esLiga){
            if(estaEnDisputa || estaFinalizada)
                myController.setScreen(Main.vistaTablaPosicionesId,this);
            else
                mostrarPopUp("La competencia no esta en Disputa o Finalizada.", "error");

        }
        else{
                mostrarPopUp("La competencia no es de la modalidad Liga", "error");
        }
    }

    public void irListarParticipantes(ActionEvent actionEvent){
        myController.setScreen(Main.vistaListarParticipantesId,this);
    }

    public void irMostrarFixture(ActionEvent actionEvent){
        competenciaDTO = gestorCompetencia.getCompetenciaDTO(idCompetencia);
        if(!competenciaDTO.getEstado().equals(Estado.CREADA.getEstadoString())) {
            myController.setScreen(Main.vistaMostrarFixtureId,this);
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
            cargarDatosCompetencia();
        }
        catch(EstadoErrorFixtureException e){
            mostrarPopUp("La competencia ya esta en Disputa o Finalizada.", "error");
        }
        catch(PocosParticipantesFixtureException e){
            mostrarPopUp("La competencia debe tener por lo menos dos participantes.", "error");
        }
        catch(DisponibilidadesInsuficientesFixtureException e){
            mostrarPopUp("La competencia no tiene suficientes Disponibilidades asignadas.", "error");
        }
        catch(FuncionalidadEnDesarrolloException e){
            mostrarPopUp("Esta funcionalidad esta en desarrollo.", "desarrollo");
        }
    }
}
