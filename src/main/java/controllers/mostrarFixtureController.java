package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML private TabPane fechas;

    @Override
    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    @Override
    public void inicializar(){
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
        title.setText(competencia.getNombre());

        generarTabs();
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

    public void generarTabs(){

        // genero 5 fechas
        // TODO: vincular con cantidad de fechas de fixture
        for(int i = 0; i<5; i++){
            Tab tab = new Tab();
            tab.setText("Fecha " + i);

            TableView tabla = new TableView();
            tabla.setPrefHeight(315.0);
            tabla.setPrefWidth(779.0);
            tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn localColumn = new TableColumn("Participante"+i);
            TableColumn resultadoColumn = new TableColumn("Resultado"+i);
            TableColumn visitanteColumn = new TableColumn("Participante"+i);
            TableColumn accionesColumn = new TableColumn("Acciones"+i);
            tabla.getColumns().addAll(localColumn, resultadoColumn, visitanteColumn, accionesColumn);

            // TODO: generar y llenar la tabla con los datos de la fecha

            tab.setContent(tabla);
            fechas.getTabs().add(tab);
        }
    }
}
