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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.*;
import services.GestorCompetencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 14/11/2015..
 */
public class mostrarFixtureController implements ControlledScreen {

    private int idCompetencia;
    private int idPartidoClickeado;
    private int fechaActual;
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

        fechaActual = gestorCompetencia.buscarFechaActual(competencia);
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
        myController.setScreen(Main.vistaVerCompetenciaId,this);
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

            TableColumn localColumn = new TableColumn("Local");
            TableColumn resultadoColumn = new TableColumn("Resultado");
            if(competencia.getSistemaPuntuacion().equals(SistemaPuntuacion.RESULTADO_FINAL)) resultadoColumn.setText("Ganador");
            TableColumn visitanteColumn = new TableColumn("Visitante");
            TableColumn accionesColumn = new TableColumn("Acciones");
            localColumn.setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("participanteLocal"));
            resultadoColumn.setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("resultado"));
            visitanteColumn.setCellValueFactory(new PropertyValueFactory<PartidoDTO, String>("participanteVisitante"));
            tabla.getColumns().clear();
            tabla.getColumns().addAll(localColumn, resultadoColumn, visitanteColumn, accionesColumn);

            agregarBotonesEnTabla(accionesColumn);

            tabla.getItems().clear();
            List<Partido> partidos = fechasComp.get(i).getPartidos();
            List<PartidoDTO> partidoDTOs = new ArrayList<PartidoDTO>();
            for(Partido part : partidos){
                if(!part.isEsLibre()){
                    PartidoDTO partDTO = new PartidoDTO();
                    partDTO.setId(part.getId());
                    partDTO.setParticipanteLocal(part.getLocal().getNombre());
                    partDTO.setParticipanteVisitante(part.getVisitante().getNombre());
                    // TODO: configurar para sets.
                    if (part.getResultados().isEmpty()){
                        partDTO.setResultado(" - ");
                    }
                    else{
                        switch (competencia.getSistemaPuntuacion()){
                            case RESULTADO_FINAL:
                                cargarResultadoCellFinal(part, partDTO);
                                break;
                            case SET:
                                cargarResultadoCellSets(part, partDTO);
                                break;
                            case PUNTUACION:
                                cargarResultadoPuntuacion(part, partDTO);
                                break;
                        }
                    }
                    partidoDTOs.add(partDTO);
                }
            }
            tabla.getItems().setAll(partidoDTOs);

            tab.setContent(tabla);
            fechas.getTabs().add(tab);
        }
        int fechaMostrada = gestorCompetencia.buscarFechaPartido(competencia, idPartidoClickeado) == -1 ? fechaActual : gestorCompetencia.buscarFechaPartido(competencia, idPartidoClickeado);
        fechas.getSelectionModel().select(fechaMostrada);
    }

    private void cargarResultadoCellSets(Partido part, PartidoDTO partDTO) {
        String resultado="";
        for(int i=0; i<part.getResultados().size();i++){
            int ptsLocal= part.getResultados().get(i).getTantosEquipoLocal();
            int ptsVisitante = part.getResultados().get(i).getTantosEquipoVisitante();

            if(ptsLocal != 0 || ptsVisitante !=0){
                if(i==0){
                    resultado+=ptsLocal+"-"+ptsVisitante;
                }
                else{
                    resultado+=" | "+ptsLocal+"-"+ptsVisitante;
                }
            }
        }
        partDTO.setResultado(resultado);
    }

    private void cargarResultadoPuntuacion(Partido part, PartidoDTO partDTO) {
        int ptsLocal = part.getResultados().get(0).getTantosEquipoLocal();
        int ptsVisit = part.getResultados().get(0).getTantosEquipoVisitante();
        if(!competencia.isAceptaEmpate() && ptsLocal == ptsVisit){
            if( part.getResultados().get(0).isGanoLocalDesempate())
                partDTO.setResultado("*"+ptsLocal+" - "+ptsVisit);
            else
                partDTO.setResultado(ptsLocal+" - "+ptsVisit+"*");
        }
        else{
            partDTO.setResultado(ptsLocal+" - "+ptsVisit);
        }
    }

    private void cargarResultadoCellFinal(Partido part, PartidoDTO partDTO) {
        int ptsLocal = part.getResultados().get(0).getTantosEquipoLocal();
        int ptsVisit = part.getResultados().get(0).getTantosEquipoVisitante();
        if(ptsLocal > ptsVisit)
            partDTO.setResultado(part.getLocal().getNombre());
        else if (ptsVisit > ptsLocal)
            partDTO.setResultado(part.getVisitante().getNombre());
        else
            partDTO.setResultado("Empate");
    }

    public void setIdPartidoClickeado(int id){
        this.idPartidoClickeado = id;
    }

    public String getSistemaCompetencia(){
        return this.competencia.getSistemaPuntuacion().getPuntuacionString();
    }

    public PrincipalController getMyController(){
        return myController;
    }

    private void agregarBotonesEnTabla(TableColumn accionesColumn){
        // Seteamos una fila con valor booleano cosa que se muestre el botón para filas no vacías
        accionesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartidoDTO, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PartidoDTO, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        mostrarFixtureController controller = this;
        accionesColumn.setCellFactory(new Callback<TableColumn<PartidoDTO, Boolean>, TableCell<PartidoDTO, Boolean>>() {
            @Override
            public TableCell<PartidoDTO, Boolean> call(TableColumn<PartidoDTO, Boolean> personBooleanTableColumn) {
                return new ResultadoCell(controller);
            }
        });
    }

    private void mostrarPopUp(){
        mostrarPopUp("", "");
    }

    private void mostrarPopUp(String mensaje, String tipo){
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

    public void mostrarPopUpResultado(String tipo){
        boolean habilitado = gestorCompetencia.partidoHabilitado(competencia, idPartidoClickeado);
        if (habilitado){
            mostrarPopUpResultadoValido(tipo);
        } else{
            mostrarPopUp("Debe terminar de cargar los resultados de las fechas anteriores.", "error");
        }
    }

    private void mostrarPopUpResultadoValido(String tipo){
        String recurso;
        switch(tipo){
            case "Sets":
                recurso = "fxml/popupGestionarResultadoS.fxml";
                break;
            case "Puntos":
                recurso = "fxml/popupGestionarResultadoP.fxml";
                break;
            case "Resultado Final":
                recurso = "fxml/popupGestionarResultadoR.fxml";
                break;
            default:
                recurso = "fxml/popupError.fxml";
                break;
        }
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(recurso));
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            ControlledScreen myScreenController = ((ControlledScreen) loader.getController());
            myController.setControladorAnterior(this);
            myScreenController.setScreenParent(myController);
            myScreenController.inicializar(idPartidoClickeado+"");
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
