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
import models.Competencia;
import models.Estado;
import models.Modalidad;
import models.Partido;
import services.GestorCompetencia;

public class verCompetenciaController extends ControlledScreen{

    private GestorCompetencia gestorCompetencia;
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
        gestorCompetencia = new GestorCompetencia();
        competenciaDTO = gestorCompetencia.getCompetencia(idCompetencia);
        title.setText(competenciaDTO.getNombre());
        deporteTextField.setText(competenciaDTO.getDeporte());
        modalidadTextField.setText(competenciaDTO.getModalidad());
        estadoTextField.setText(competenciaDTO.getEstado());
        boolean estaEnDisputa = competenciaDTO.getEstado().equals(Estado.EN_DISPUTA.getEstadoString());
        boolean estaPlanificada = competenciaDTO.getEstado().equals(Estado.PLANIFICADA.getEstadoString());
        boolean estaFinalizada = competenciaDTO.getEstado().equals(Estado.FINALIZADA.getEstadoString());
        boolean habilitado = (estaEnDisputa || estaPlanificada && !estaFinalizada) ? true : false;
        setProximoEncuentro(habilitado);
    }

    @Override
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
            setProximoEncuentro(true);
            estadoTextField.setText(Estado.PLANIFICADA.getEstadoString());
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

    public void setProximoEncuentro(boolean habilitado){
        if (habilitado) {
            Competencia competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
            int fechaActual = gestorCompetencia.buscarFechaActual(competencia);
            Partido proximoEncuentro = gestorCompetencia.getProxEncuentro(competencia, fechaActual);
            String proximoEncuentroMensaje = proximoEncuentro.getLocal().getNombre() + " - " + proximoEncuentro.getVisitante().getNombre();
            proximoEncuentroTextField.setText(proximoEncuentroMensaje);
        } else {
            proximoEncuentroTextField.setText(" - ");
        }
    }
}
