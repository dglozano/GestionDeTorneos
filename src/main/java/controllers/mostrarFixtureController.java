package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Competencia;
import services.GestorCompetencia;

/**
 * Created by DIego on 14/11/2015..
 */
public class mostrarFixtureController implements ControlledScreen {

    private int idCompetencia;
    private Competencia competencia;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private PrincipalController myController;
    private Stage modal;
    private Parent parent;

    @FXML private Text title;

    @Override
    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    @Override
    public void inicializar(){
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
        title.setText(competencia.getNombre());
    }

    @Override
    public void inicializar(String string){
        inicializar();
    }

    @Override
    public Object mensajeControladorAnterior() {
        return idCompetencia;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId);
    }
}
