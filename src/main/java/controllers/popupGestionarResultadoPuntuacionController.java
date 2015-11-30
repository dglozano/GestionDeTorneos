package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.ResultadoPuntuacionDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Partido;
import services.GestorCompetencia;

public class popupGestionarResultadoPuntuacionController implements ControlledScreen {
    private PrincipalController myController;
    private Stage modal;
    private int idCompetencia;
    private int idPartidoClickeado;
    private Partido partido;
    private Parent parent;
    private boolean aceptaEmpates;
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
    @FXML private Label mensajeEmpate;
    @FXML private Label mensajeError;
    @FXML private Spinner puntajeLocalSpinner;
    @FXML private Spinner puntajeVisitanteSpinner;
    @FXML private CheckBox sePresentoLocalCheckBox;
    @FXML private CheckBox sePresentoVisitanteCheckBox;
    @FXML private ToggleGroup desempateToggleGroup;
    @FXML private RadioButton ganoLocalRadioButton;
    @FXML private RadioButton ganoVisitanteRadioButton;


    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        partido = gestorCompetencia.buscarPartidoPorId(idPartidoClickeado);
        aceptaEmpates = gestorCompetencia.buscarCompetenciaPorId(idCompetencia).isAceptaEmpate();
        sePresentoLocalCheckBox.setText(partido.getLocal().getNombre());
        sePresentoVisitanteCheckBox.setText(partido.getVisitante().getNombre());
        ganoLocalRadioButton.setText(partido.getLocal().getNombre());
        ganoVisitanteRadioButton.setText(partido.getVisitante().getNombre());
        puntajeLocalSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        puntajeVisitanteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        puntajeVisitanteSpinner.valueProperty().removeListener(listenerSpinner);
        puntajeLocalSpinner.valueProperty().removeListener(listenerSpinner);
        puntajeLocalSpinner.valueProperty().addListener(listenerSpinner);
        puntajeVisitanteSpinner.valueProperty().addListener(listenerSpinner);
        puntajeVisitanteSpinner.setDisable(true);
        puntajeLocalSpinner.setDisable(true);
        mensajeEmpate.setVisible(false);
        ganoLocalRadioButton.setVisible(false);
        ganoVisitanteRadioButton.setVisible(false);
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
        if(validar()){
            ResultadoPuntuacionDTO resultadoPuntuacionDTO = new ResultadoPuntuacionDTO();
            cargarResultadoDto(resultadoPuntuacionDTO);
            gestorCompetencia.cargarResultadoPuntuacion(resultadoPuntuacionDTO);
            myController.getControladorAnterior().inicializar();
            myController.setControladorAnterior(this);
            Stage modal = (Stage)okButton.getScene().getWindow();
            modal.close();
        }

    }

    private void cargarResultadoDto(ResultadoPuntuacionDTO resultadoPuntuacionDTO) {
        resultadoPuntuacionDTO.setIdCompetencia(idCompetencia);
        resultadoPuntuacionDTO.setIdPartido(idPartidoClickeado);
        if(sePresentoLocalCheckBox.isSelected() && sePresentoVisitanteCheckBox.isSelected()){
            resultadoPuntuacionDTO.setTantosLocal((Integer)puntajeLocalSpinner.getValue());
            resultadoPuntuacionDTO.setTantosVisitante((Integer)puntajeVisitanteSpinner.getValue());
            resultadoPuntuacionDTO.setSePresentoLocal(true);
            resultadoPuntuacionDTO.setSePresentoVisitante(true);
            if(!aceptaEmpates &&((Integer)puntajeLocalSpinner.getValue() == (Integer)puntajeVisitanteSpinner.getValue())){
                resultadoPuntuacionDTO.setTieneDesempate(true);
                if(((RadioButton)desempateToggleGroup.getSelectedToggle()).equals(ganoLocalRadioButton)){
                    resultadoPuntuacionDTO.setGanoLocalDesempate(true);
                }
                else{
                    resultadoPuntuacionDTO.setGanoLocalDesempate(false);
                }
            }
        }
        else{
            resultadoPuntuacionDTO.setSePresentoVisitante(sePresentoVisitanteCheckBox.isSelected());
            resultadoPuntuacionDTO.setSePresentoLocal(sePresentoLocalCheckBox.isSelected());
            resultadoPuntuacionDTO.setTantosLocal(0);
            resultadoPuntuacionDTO.setTantosVisitante(0);
        }
    }

    private boolean validar() {
        if(ganoLocalRadioButton.isVisible() && ganoVisitanteRadioButton.isVisible()){
            if(desempateToggleGroup.getSelectedToggle() == null){
                mensajeError.setText("La competencia no acepta empates. Elija el ganador.");
                mensajeError.setVisible(true);
                return false;
            }
        }
        if(!sePresentoLocalCheckBox.isSelected() && !sePresentoVisitanteCheckBox.isSelected()){
            mensajeError.setText("Al menos un participante debe haberse presentado");
            mensajeError.setVisible(true);
            return false;
        }
        mensajeError.setVisible(false);
        return true;
    }

    public void spinnerChange(){
        if(!aceptaEmpates && ((Integer)puntajeLocalSpinner.getValue() == (Integer)puntajeVisitanteSpinner.getValue())){
            mensajeEmpate.setVisible(true);
            ganoLocalRadioButton.setVisible(true);
            ganoVisitanteRadioButton.setVisible(true);
        }
        else{
            mensajeEmpate.setVisible(false);
            ganoLocalRadioButton.setVisible(false);
            ganoVisitanteRadioButton.setVisible(false);
            if(desempateToggleGroup.getSelectedToggle() != null) desempateToggleGroup.getSelectedToggle().setSelected(false);
        }
    }

    public void checkBoxClicked(ActionEvent actionEvent){
       if(sePresentoVisitanteCheckBox.isSelected() && sePresentoLocalCheckBox.isSelected()) {
           puntajeVisitanteSpinner.setDisable(false);
           puntajeLocalSpinner.setDisable(false);
           spinnerChange();
       }
       else{
           puntajeVisitanteSpinner.setDisable(true);
           puntajeLocalSpinner.setDisable(true);
           mensajeEmpate.setVisible(false);
           ganoLocalRadioButton.setVisible(false);
           ganoVisitanteRadioButton.setVisible(false);
           if(desempateToggleGroup.getSelectedToggle() != null) desempateToggleGroup.getSelectedToggle().setSelected(false);
       }
    }

}
