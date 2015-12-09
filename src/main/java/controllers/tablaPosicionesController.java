package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.FilaPosicionDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.Competencia;
import models.SistemaPuntuacion;
import services.GestorCompetencia;

import java.util.List;

public class tablaPosicionesController extends ControlledScreen {

    private GestorCompetencia gestorCompetencia;
    private Competencia competencia;

    @FXML private Text title;
    @FXML private TableView posicionesTableView;
    @FXML private TableColumn nombreColumna;
    @FXML private TableColumn puntosColumna;
    @FXML private TableColumn jugadosColumna;
    @FXML private TableColumn ganadosColumna;
    @FXML private TableColumn empatadosColumna;
    @FXML private TableColumn perdidosColumna;
    @FXML private TableColumn favorColumna;
    @FXML private TableColumn contraColumna;
    @FXML private TableColumn diferenciaColumna;

    @Override
    public void inicializar(){
        buscarCompetencia();
        title.setText(competencia.getNombre());
        setearColumnas();
        cargarFilas();

    }

    private void cargarFilas() {
        List<FilaPosicionDTO> filasPosicion= gestorCompetencia.tablaPosiciones(competencia.getId());
        posicionesTableView.getItems().clear();
        posicionesTableView.getItems().addAll(filasPosicion);

    }

    private void buscarCompetencia() {
        gestorCompetencia = new GestorCompetencia();
        int idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        competencia = gestorCompetencia.buscarCompetenciaPorId(idCompetencia);
    }

    private void setearColumnas() {
        posicionesTableView.getColumns().clear();
        posicionesTableView.getColumns().addAll(nombreColumna,puntosColumna,jugadosColumna,
                ganadosColumna,empatadosColumna,perdidosColumna,favorColumna,contraColumna,diferenciaColumna);
        nombreColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO, String>("nombreParticipante"));
        puntosColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("puntos"));
        jugadosColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("jugados"));
        ganadosColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("ganados"));
        empatadosColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("empatados"));
        perdidosColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("perdidos"));
        favorColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("favor"));
        contraColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("contra"));
        diferenciaColumna.setCellValueFactory(new PropertyValueFactory<FilaPosicionDTO,Integer>("diferencia"));
        SistemaPuntuacion sistemaPuntuacion = competencia.getSistemaPuntuacion();
        if(!sistemaPuntuacion.equals(SistemaPuntuacion.PUNTUACION)){
            posicionesTableView.getColumns().remove(6,9);
        }
        if(sistemaPuntuacion.equals(SistemaPuntuacion.SET) || !competencia.isAceptaEmpate()){
            posicionesTableView.getColumns().remove(4);
        }
        calcularAnchoColumnaNombre();
    }

    private void calcularAnchoColumnaNombre() {
        posicionesTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        double prefWidth= 896.0;
        for(TableColumn columna: (List<TableColumn>)posicionesTableView.getColumns()){
            if(!columna.getId().equals("nombreColumna"))
                prefWidth-=columna.getWidth();
        }

        nombreColumna.setResizable(true);
        nombreColumna.setPrefWidth(prefWidth);
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId,this);
    }

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public Object mensajeControladorAnterior(){
        return competencia.getId();
    }

    public void irImprimir(ActionEvent actionEvent) {
        mostrarPopUp("Esta funcionalidad esta en desarrollo","desarrollo");
    }

    public void irExportar(ActionEvent actionEvent) {
        mostrarPopUp("Esta funcionalidad esta en desarrollo","desarrollo");
    }

}
