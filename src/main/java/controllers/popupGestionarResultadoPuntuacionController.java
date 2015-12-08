package controllers;

import controllers.general.ControlledScreen;
import dtos.ResultadoPuntuacionDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Partido;
import models.Resultado;
import services.GestorCompetencia;

import java.util.List;

public class popupGestionarResultadoPuntuacionController extends ControlledScreen {

    private int idCompetencia;
    private int idPartidoClickeado;
    private Partido partido;
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

    @Override
    public void inicializar() {
        inicializacionBasica();
        if(partido.getResultados().isEmpty()){
            inicializacionPrimeraVez();
        }
        else{
            cargarResultadoAnterior();
        }
    }

    @Override
    public void inicializar(String mensaje){
        idPartidoClickeado = Integer.parseInt(mensaje);
        inicializar();
    }

    private void cargarResultadoAnterior() {
        Resultado resultadoAnterior = partido.getResultados().get(0);
        sePresentoLocalCheckBox.setSelected(resultadoAnterior.isJugoLocal());
        sePresentoVisitanteCheckBox.setSelected(resultadoAnterior.isJugoVisitante());
        int tantosLocal = resultadoAnterior.getTantosEquipoLocal();
        int tantosVisitante = resultadoAnterior.getTantosEquipoVisitante();
        boolean desempateGanoLocal = resultadoAnterior.isGanoLocalDesempate();
        puntajeLocalSpinner.getValueFactory().setValue(tantosLocal);
        puntajeVisitanteSpinner.getValueFactory().setValue(tantosVisitante);
        if(desempateGanoLocal){
            ganoLocalRadioButton.setSelected(true);
        }
        else{
            ganoVisitanteRadioButton.setSelected(true);
        }
        habilitarComponentesCorrespondientes();
    }

    private void inicializacionPrimeraVez() {
        puntajeVisitanteSpinner.setDisable(true);
        puntajeLocalSpinner.setDisable(true);
        mensajeEmpate.setVisible(false);
        ganoLocalRadioButton.setVisible(false);
        ganoVisitanteRadioButton.setVisible(false);
        sePresentoLocalCheckBox.setSelected(false);
        sePresentoVisitanteCheckBox.setSelected(false);
    }

    private void inicializacionBasica() {
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
    }

    @Override
    public Object mensajeControladorAnterior(){ return idCompetencia; }

    public void cancelar(ActionEvent actionEvent){
        volver();
    }

    private void volver() {
        ControlledScreen anterior = myController.getControladorAnterior();
        myController.setControladorAnterior(this);
        Stage modal = (Stage)cancelarButton.getScene().getWindow();
        modal.close();
        anterior.inicializar();

    }

    public void aceptar(ActionEvent actionEvent){
        if(validar()){
            ResultadoPuntuacionDTO resultadoPuntuacionDTO = new ResultadoPuntuacionDTO();
            cargarResultadoDto(resultadoPuntuacionDTO);
            if(huboCambios(resultadoPuntuacionDTO,partido.getResultados())){
                gestorCompetencia.cargarResultado(resultadoPuntuacionDTO);
            }
            volver();
        }
    }

    private boolean huboCambios(ResultadoPuntuacionDTO resultadoPuntuacionDTO, List<Resultado> resultados) {
        if(resultados.isEmpty()){
            return true;
        }
        Resultado resultadoAnterior = resultados.get(0);
        if(resultadoAnterior.isJugoVisitante() != resultadoPuntuacionDTO.isSePresentoVisitante()){
            return true;
        }
        if(resultadoAnterior.isJugoLocal() != resultadoPuntuacionDTO.isSePresentoLocal()){
            return true;
        }
        if(resultadoAnterior.getTantosEquipoLocal() != (Integer)puntajeLocalSpinner.getValue()){
            return true;
        }
        if(resultadoAnterior.getTantosEquipoVisitante() != (Integer)puntajeVisitanteSpinner.getValue()){
            return true;
        }
        if(resultadoAnterior.isGanoLocalDesempate() != resultadoPuntuacionDTO.isGanoLocalDesempate()){
            return true;
        }
        return false;
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
        habilitarComponentesCorrespondientes();
    }

    private void habilitarComponentesCorrespondientes() {
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
