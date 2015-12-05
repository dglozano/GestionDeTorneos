package controllers;

import app.Main;
import controllers.general.ButtonParticipanteCell;
import controllers.general.ControlledScreen;
import dtos.ParticipanteDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.Competencia;
import models.Estado;
import services.GestorCompetencia;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class listarParticipantesController extends ControlledScreen {

    private int idCompetencia;
    private Competencia competencia;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();

    @FXML private Text title;
    @FXML private TableView participantesTableView;
    @FXML private TableColumn nombreParticipanteColumn;
    @FXML private TableColumn emailParticipanteColumn;
    @FXML private TableColumn editarColumn;
    @FXML private TableColumn eliminarColumn;

    @Override
    public void inicializar(){
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
        title.setText(competencia.getNombre());
        inicializarTabla();
    }

    private void inicializarTabla() {
        List<ParticipanteDTO> listaParticipantesDTOs = gestorCompetencia.listarParticipantesDtos(idCompetencia);
        Collections.sort(listaParticipantesDTOs,new ParticipanteDTOComparator<ParticipanteDTO>());
        nombreParticipanteColumn.setCellValueFactory(new PropertyValueFactory<ParticipanteDTO, String>("nombreParticipante"));
        emailParticipanteColumn.setCellValueFactory(new PropertyValueFactory<ParticipanteDTO, String>("emailParticipante"));
        agregarBotonesEnTabla();
        participantesTableView.getItems().clear();
        participantesTableView.getItems().setAll(listaParticipantesDTOs);

    }

    private void agregarBotonesEnTabla() {
        editarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParticipanteDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ParticipanteDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        eliminarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParticipanteDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ParticipanteDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        listarParticipantesController controller = this;
        editarColumn.setCellFactory(new Callback<TableColumn<ParticipanteDTO, Boolean>, TableCell<ParticipanteDTO, Boolean>>() {
            @Override
            public TableCell<ParticipanteDTO, Boolean> call(TableColumn<ParticipanteDTO, Boolean> personBooleanTableColumn) {
                return new ButtonParticipanteCell("Editar", controller);
            }
        });
        eliminarColumn.setCellFactory(new Callback<TableColumn<ParticipanteDTO, Boolean>, TableCell<ParticipanteDTO, Boolean>>() {
            @Override
            public TableCell<ParticipanteDTO, Boolean> call(TableColumn<ParticipanteDTO, Boolean> personBooleanTableColumn) {
                return new ButtonParticipanteCell("Eliminar", controller);
            }
        });
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId);
    }

    public void irAltaParticipante(ActionEvent actionEvent){
        Estado estadoCompetencia = competencia.getEstado();
        if(estadoCompetencia.equals(Estado.CREADA) || estadoCompetencia.equals(Estado.PLANIFICADA) ){
            myController.setScreen(Main.vistaAltaParticipanteId);
        }
        else{
            mostrarPopUp("La competencia ya esta en Disputa o Finalizada","error");
        }
    }

    @Override
    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

    class ParticipanteDTOComparator<T> implements Comparator<T> {
        @Override
        public int compare(T a, T b) {
            return ((ParticipanteDTO)a).getNombreParticipante().compareTo(((ParticipanteDTO) b).getNombreParticipante());
        }
    }

}
