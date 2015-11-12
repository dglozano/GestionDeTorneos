package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Competencia;
import models.SistemaPuntuacion;
import services.GestorCompetencia;

import java.util.List;


/**
 * Created by DIego on 10/11/2015..
 */
public class tablaPosicionesController implements ControlledScreen {

    private GestorCompetencia gestorCompetencia;
    private PrincipalController myController;
    private Competencia competencia;

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
        setearColumnas();
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
        /*TODO 00: No se por que la primera vez el ancho de la tabla es cero. Puse que te tome el prefWidth si es 0, pero queda apenas mal"*/
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
}
