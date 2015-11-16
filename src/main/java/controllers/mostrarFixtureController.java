package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import controllers.general.ResultadoCell;
import dtos.PartidoDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Competencia;
import models.Fecha;
import models.Fixture;
import models.Partido;
import services.GestorCompetencia;

import java.util.ArrayList;
import java.util.List;

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
    private Fixture fixture;


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

        fixture = competencia.getFixture();
        List<Fecha> listaFechas = fixture.getFechas();
        generarTabs(listaFechas);
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

    public void generarTabs(List<Fecha> fechasComp){

        int cantFechas = fechasComp.size();
        fechas.getTabs().clear();

        for(int i = 0; i<cantFechas; i++){
            Tab tab = new Tab();
            tab.setText("Fecha " + fechasComp.get(i).getNumeroFecha());

            TableView tabla = new TableView();
            tabla.setPrefHeight(315.0);
            tabla.setPrefWidth(779.0);
            tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn localColumn = new TableColumn("Participante");
            TableColumn resultadoColumn = new TableColumn("Resultado");
            TableColumn visitanteColumn = new TableColumn("Participante");
            TableColumn accionesColumn = new TableColumn("Acciones");
            tabla.getColumns().addAll(localColumn, resultadoColumn, visitanteColumn, accionesColumn);
            ((TableColumn)tabla.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("partiLocal"));
            ((TableColumn)tabla.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("result"));
            ((TableColumn)tabla.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("partiVisit"));

            agregarBotonesEnTabla(accionesColumn);

            tabla.getItems().clear();
            List<Partido> partidos = fechasComp.get(i).getPartidos();
            List<PartidoDTO> partidoDTOs = new ArrayList<PartidoDTO>();
            for(Partido part : partidos){
                PartidoDTO partDTO = new PartidoDTO();
                partDTO.setPartiLocal(part.getLocal().getNombre());
                partDTO.setPartiVisit(part.getVisitante().getNombre());
                // TODO: configurar para sets.
                if (part.getResultados().isEmpty()){
                    partDTO.setResult(" - ");
                }
                else{
                    int ptsLocal = part.getResultados().get(0).getTantosEquipoLocal();
                    int ptsVisit = part.getResultados().get(0).getTantosEquipoVisitante();
                    partDTO.setResult(ptsLocal + " - " + ptsVisit);
                }
                partidoDTOs.add(partDTO);
            }
            tabla.getItems().setAll(partidoDTOs);

            tab.setContent(tabla);
            fechas.getTabs().add(tab);
        }
    }

    private void agregarBotonesEnTabla(TableColumn accionesColumn){
        // Seteamos una fila con valor booleano cosa que se muestre el botón para filas no vacías
        accionesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PartidoDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });

        // Creamos una nueva factory de cell con un botón de Ver competencia
        mostrarFixtureController controller = this;
        accionesColumn.setCellFactory(new Callback<TableColumn<PartidoDTO, Boolean>, TableCell<PartidoDTO, Boolean>>() {
            @Override
            public TableCell<PartidoDTO, Boolean> call(TableColumn<PartidoDTO, Boolean> personBooleanTableColumn) {
                return new ResultadoCell(controller);
            }
        });
    }
}
