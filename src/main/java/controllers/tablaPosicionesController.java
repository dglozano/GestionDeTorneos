package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.FilaPosicionDTO;
import dtos.ParticipanteDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.*;
import services.GestorCompetencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by DIego on 10/11/2015..
 */
public class tablaPosicionesController implements ControlledScreen {

    private GestorCompetencia gestorCompetencia;
    private PrincipalController myController;
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

    public void inicializar(){
        buscarCompetencia();
        title.setText(competencia.getNombre());
        setearColumnas();
        cargarFilas();

    }

    private void cargarFilas() {
        List<FilaPosicionDTO> filasPosicion= gestorCompetencia.tablaPosiciones(competencia.getId());
        posicionesTableView.getItems().clear();
        Collections.sort(filasPosicion, new PosicionComparator<FilaPosicionDTO>());
        posicionesTableView.getItems().addAll(filasPosicion);

    }

    public void inicializar(String mensaje) {inicializar();};

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
            posicionesTableView.getColumns().remove(6,8);
        }
        if(sistemaPuntuacion.equals(SistemaPuntuacion.SET) || !competencia.isAceptaEmpate()){
            posicionesTableView.getColumns().remove(4);
        }
        calcularAnchoColumnaNombre();
    }

    private void calcularAnchoColumnaNombre() {
        posicionesTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        double prefWidth= posicionesTableView.getWidth();
        if(prefWidth == 0.0) prefWidth=898.0;
        for(TableColumn columna: (List<TableColumn>)posicionesTableView.getColumns()){
            if(!columna.getId().equals("nombreColumna"))
                prefWidth-=columna.getWidth();
        }

        nombreColumna.setResizable(true);
        nombreColumna.setPrefWidth(prefWidth);
    }

    public void volver(ActionEvent actionEvent){
        myController.setScreen(Main.vistaVerCompetenciaId);
    }

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public Object mensajeControladorAnterior(){
        return competencia.getId();
    }

    class PosicionComparator<T> implements Comparator<T> {
        @Override
        public int compare(T a, T b) {
            Integer puntosA =((Integer)((FilaPosicionDTO)a).getPuntos());
            Integer puntosB =((Integer)((FilaPosicionDTO)b).getPuntos());
            Integer diferenciaA =((Integer)((FilaPosicionDTO)a).getDiferencia());
            Integer diferenciaB =((Integer)((FilaPosicionDTO)b).getDiferencia());
            Integer favorA =((Integer)((FilaPosicionDTO)b).getFavor());
            Integer favorB =((Integer)((FilaPosicionDTO)b).getFavor());
            String nombreA =((String)((FilaPosicionDTO)b).getNombreParticipante());
            String nombreB =((String)((FilaPosicionDTO)b).getNombreParticipante());
            if(puntosA.compareTo(puntosB) != 0){
                return puntosA.compareTo(puntosB)*(-1);
            }
            if(diferenciaA.compareTo(diferenciaB) !=0){
                return diferenciaA.compareTo(diferenciaB)*(-1);
            }
            if(favorA.compareTo(favorB) != 0){
                return favorA.compareTo(favorB)*(-1);
            }
            else{
                return nombreA.compareTo(nombreB)*(-1);
            }
        }
    }
}
