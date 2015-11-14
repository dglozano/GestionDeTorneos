package controllers;

import app.Main;
import controllers.general.ButtonParticipanteCell;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.ParticipanteDTO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Competencia;
import services.GestorCompetencia;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DIego on 11/11/2015..
 */
public class listarParticipantesController implements ControlledScreen {

    private int idCompetencia;
    private Competencia competencia;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private PrincipalController myController;
    private Stage modal;
    private Parent parent;

    @FXML private Text title;
    @FXML private TableView participantesTableView;
    @FXML private TableColumn nombreParticipanteColumn;
    @FXML private TableColumn emailParticipanteColumn;
    @FXML private TableColumn editarColumn;
    @FXML private TableColumn eliminarColumn;


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

    public void inicializar(String mensaje) {inicializar();};

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId);
    }

    public void irAltaParticipante(ActionEvent actionEvent){
        myController.setScreen(Main.vistaAltaParticipanteId);
    }

    public Object mensajeControladorAnterior(){
        return idCompetencia;
    }

    class ParticipanteDTOComparator<T> implements Comparator<T> {
        @Override
        public int compare(T a, T b) {
            return ((ParticipanteDTO)a).getNombreParticipante().compareTo(((ParticipanteDTO) b).getNombreParticipante());
        }
    }

    public void mostrarPopUp(){
        mostrarPopUp("","");
    }

    public void mostrarPopUp(String mensaje, String tipo){
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
