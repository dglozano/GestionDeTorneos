package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Partido;
import services.GestorCompetencia;

import java.util.ArrayList;

/**
 * Created by DIego on 21/11/2015..
 */
public class popupGestionarResultadoSetsController implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;
    private int idCompetencia;
    private int idPartidoClickeado;
    private int cantSets;
    private Partido partido;
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private ChangeListener<Integer> listenerSpinner = new ChangeListener<Integer>() {
        @Override
        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
            spinnerChange();
        }
    };

    @FXML private Button okButton;
    @FXML private Button cancelarButton;
    @FXML private Label detailsLabel;
    @FXML private Label error1;
    @FXML private CheckBox sePresentoLocalCheckBox;
    @FXML private CheckBox sePresentoVisitanteCheckBox;
    @FXML private Spinner localSpinner1;
    @FXML private Spinner localSpinner2;
    @FXML private Spinner localSpinner3;
    @FXML private Spinner localSpinner4;
    @FXML private Spinner localSpinner5;
    @FXML private Spinner localSpinner6;
    @FXML private Spinner localSpinner7;
    @FXML private Spinner localSpinner8;
    @FXML private Spinner localSpinner9;
    @FXML private Spinner visitanteSpinner1;
    @FXML private Spinner visitanteSpinner2;
    @FXML private Spinner visitanteSpinner3;
    @FXML private Spinner visitanteSpinner4;
    @FXML private Spinner visitanteSpinner5;
    @FXML private Spinner visitanteSpinner6;
    @FXML private Spinner visitanteSpinner7;
    @FXML private Spinner visitanteSpinner8;
    @FXML private Spinner visitanteSpinner9;

    private ArrayList<Spinner> setsLocal = new ArrayList<>();
    private ArrayList<Spinner> setsVisitante = new ArrayList<>();

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        partido = gestorCompetencia.buscarPartidoPorId(idPartidoClickeado);
        sePresentoLocalCheckBox.setText(partido.getLocal().getNombre());
        sePresentoVisitanteCheckBox.setText(partido.getVisitante().getNombre());
        sePresentoLocalCheckBox.setSelected(false);
        sePresentoLocalCheckBox.setSelected(false);
        cantSets= gestorCompetencia.buscarCompetenciaPorId(idCompetencia).getCantidadDeSets();
        cargarListaSpinners();
        prepararSpinners();
        error1.setVisible(false);
    }

    private void prepararSpinners() {
        for(int i=0;i<9;i++){
            if(i>=cantSets){
                setsLocal.get(i).setVisible(false);
                setsVisitante.get(i).setVisible(false);
            }
            else{
                setsLocal.get(i).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));setsVisitante.get(i).setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
                setsLocal.get(i).valueProperty().removeListener(listenerSpinner);
                setsVisitante.get(i).valueProperty().removeListener(listenerSpinner);
                setsLocal.get(i).valueProperty().addListener(listenerSpinner);
                setsVisitante.get(i).valueProperty().addListener(listenerSpinner);
                setsLocal.get(i).setDisable(true);
                setsVisitante.get(i).setDisable(true);
                setsLocal.get(i).setVisible(true);
                setsVisitante.get(i).setVisible(true);
            }
        }
    }

    private void cargarListaSpinners() {
        setsLocal.add(localSpinner1);
        setsLocal.add(localSpinner2);
        setsLocal.add(localSpinner3);
        setsLocal.add(localSpinner4);
        setsLocal.add(localSpinner5);
        setsLocal.add(localSpinner6);
        setsLocal.add(localSpinner7);
        setsLocal.add(localSpinner8);
        setsLocal.add(localSpinner9);
        setsVisitante.add(visitanteSpinner1);
        setsVisitante.add(visitanteSpinner2);
        setsVisitante.add(visitanteSpinner3);
        setsVisitante.add(visitanteSpinner4);
        setsVisitante.add(visitanteSpinner5);
        setsVisitante.add(visitanteSpinner6);
        setsVisitante.add(visitanteSpinner7);
        setsVisitante.add(visitanteSpinner8);
        setsVisitante.add(visitanteSpinner9);
    }

    public void inicializar(String mensaje){
        idPartidoClickeado = Integer.parseInt(mensaje);
        inicializar();
    }

    public Object mensajeControladorAnterior(){ return idCompetencia; }

    public void cancelar(ActionEvent actionEvent){
        myController.setControladorAnterior(this);
        Stage modal = (Stage)cancelarButton.getScene().getWindow();
        modal.close();
    }

    public void aceptar(ActionEvent actionEvent){
       /* if(validar()){
            ResultadoPuntuacionDTO resultadoPuntuacionDTO = new ResultadoPuntuacionDTO();
            cargarResultadoDto(resultadoPuntuacionDTO);
            gestorCompetencia.cargarResultadoPuntuacion(resultadoPuntuacionDTO);
            myController.getControladorAnterior().inicializar();
            myController.setControladorAnterior(this);
            Stage modal = (Stage)okButton.getScene().getWindow();
            modal.close();
        }*/
        myController.getControladorAnterior().inicializar();
        myController.setControladorAnterior(this);
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }

    public void spinnerChange(){
        habilitarSpinnersCorrespondientes();
        if(!setsLocal.get(cantSets-1).isDisabled() && !setsVisitante.get(cantSets-1).isDisabled()){
            int tantosLocal = (Integer)setsLocal.get(cantSets-1).getValue();
            int tantosVisitante = (Integer)setsVisitante.get(cantSets-1).getValue();
            if(tantosLocal == tantosVisitante){
                error1.setText("No se puede empatar un set");
                error1.setVisible(true);
            }
            else{
                error1.setVisible(false);
            }
        }
    }

    public void checkBoxClicked(ActionEvent actionEvent){
        if(sePresentoLocalCheckBox.isSelected() && sePresentoVisitanteCheckBox.isSelected()){
            setsLocal.get(0).setDisable(false);
            setsVisitante.get(0).setDisable(false);
            habilitarSpinnersCorrespondientes();
        }
        else{
            for(int i=0;i<cantSets;i++){
                setsLocal.get(i).setDisable(true);
                setsVisitante.get(i).setDisable(true);
            }
        }
    }

    private void habilitarSpinnersCorrespondientes() {
        int cantSetsGanadosLocal = 0;
        int cantSetsGanadosVisitante = 0;
        for(int i=0;i<cantSets-1;i++){
            int tantosLocal = (Integer)setsLocal.get(i).getValue();
            int tantosVisitante = (Integer)setsVisitante.get(i).getValue();
            if(setsLocal.get(i).isDisabled() || setsVisitante.get(i).isDisabled()){
                setsLocal.get(i+1).setDisable(true);
                setsVisitante.get(i+1).setDisable(true);
            }
            else{
                if(tantosLocal>tantosVisitante) cantSetsGanadosLocal ++;
                if(tantosLocal<tantosVisitante) cantSetsGanadosVisitante++;
                boolean yaGanoLocal = cantSetsGanadosLocal == cantSets/2+1;
                boolean yaGanoVisitante = cantSetsGanadosVisitante == cantSets/2+1;
                if(tantosLocal == tantosVisitante){
                    setsLocal.get(i+1).setDisable(true);
                    setsVisitante.get(i+1).setDisable(true);
                    error1.setText("No se puede empatar un set");
                    error1.setVisible(true);
                }
                else if (yaGanoLocal || yaGanoVisitante){
                    error1.setVisible(false);
                    setsLocal.get(i+1).setDisable(true);
                    setsVisitante.get(i+1).setDisable(true);
                }
                else{
                    setsLocal.get(i+1).setDisable(false);
                    setsVisitante.get(i+1).setDisable(false);
                }
            }
        }
    }
}

