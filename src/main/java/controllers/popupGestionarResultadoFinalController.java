package controllers;

import controllers.general.ControlledScreen;
import dtos.ResultadoFinalDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Partido;
import models.Resultado;
import services.GestorCompetencia;
import services.GestorResultado;

import java.util.List;

public class popupGestionarResultadoFinalController extends ControlledScreen {

    private int idCompetencia;
    private int idPartidoClickeado;
    private boolean aceptaEmpates;
    private GestorResultado gestorResultado = new GestorResultado();
    private GestorCompetencia gestorCompetencia = new GestorCompetencia();
    private Partido partido;

    @FXML private Button okButton;
    @FXML private Button cancelarButton;
    @FXML private Label detailsLabel;
    @FXML private ToggleGroup ganadorToggleGroup;
    @FXML private RadioButton ganoLocalRadioButton;
    @FXML private RadioButton ganoVisitanteRadioButton;
    @FXML private RadioButton empateRadioButton;
    @FXML private CheckBox sePresentoLocalCheckBox;
    @FXML private CheckBox sePresentoVisitanteCheckBox;
    @FXML private Label errorLabel;

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

    private void inicializacionBasica() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        partido = gestorCompetencia.buscarPartidoPorId(idPartidoClickeado);
        aceptaEmpates = gestorCompetencia.buscarCompetenciaPorId(idCompetencia).isAceptaEmpate();
        sePresentoLocalCheckBox.setText(partido.getLocal().getNombre());
        sePresentoVisitanteCheckBox.setText(partido.getVisitante().getNombre());
        ganoLocalRadioButton.setText(partido.getLocal().getNombre());
        ganoVisitanteRadioButton.setText(partido.getVisitante().getNombre());
        errorLabel.setVisible(false);
        if(aceptaEmpates) empateRadioButton.setVisible(true);
        else empateRadioButton.setVisible(false);
    }

    private void cargarResultadoAnterior() {
        Resultado resultadoAnterior = partido.getResultados().get(0);
        sePresentoLocalCheckBox.setSelected(resultadoAnterior.isJugoLocal());
        sePresentoVisitanteCheckBox.setSelected(resultadoAnterior.isJugoVisitante());
        int tantosLocal = resultadoAnterior.getTantosEquipoLocal();
        int tantosVisitante = resultadoAnterior.getTantosEquipoVisitante();
        habilitarRadiosCorrespondientes();
        if (tantosLocal>tantosVisitante){
            ganoLocalRadioButton.setSelected(true);
        }
        else if(tantosVisitante>tantosLocal){
            ganoVisitanteRadioButton.setSelected(true);
        }
        else{
            empateRadioButton.setSelected(true);
        }
    }

    private void inicializacionPrimeraVez() {
        sePresentoLocalCheckBox.setSelected(false);
        sePresentoVisitanteCheckBox.setSelected(false);
        if(ganadorToggleGroup.getSelectedToggle() != null) ganadorToggleGroup.getSelectedToggle().setSelected(false);
        ganoLocalRadioButton.setDisable(true);
        ganoVisitanteRadioButton.setDisable(true);
        empateRadioButton.setDisable(true);
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
            ResultadoFinalDTO resultadoDTO = new ResultadoFinalDTO();
            cargarResultadoDto(resultadoDTO);
            if(huboCambios(resultadoDTO, partido.getResultados())){
                gestorCompetencia.cargarResultado(resultadoDTO);
            }
            volver();
        }
    }

    private boolean huboCambios(ResultadoFinalDTO resultadoDTO, List<Resultado> resultados) {
        if(resultados.isEmpty()){
            return true;
        }
        Resultado resultadoAnterior = resultados.get(0);
        if(resultadoAnterior.isJugoVisitante() != resultadoDTO.isSePresentoVisitante()){
            return true;
        }
        if(resultadoAnterior.isJugoLocal() != resultadoDTO.isSePresentoLocal()){
            return true;
        }
        boolean eraEmpate = resultadoAnterior.getTantosEquipoLocal() == resultadoAnterior.getTantosEquipoVisitante();
        if(resultadoDTO.isEmpate() != eraEmpate){
            return true;
        }
        boolean ganoVisitante = resultadoAnterior.getTantosEquipoVisitante() > resultadoAnterior.getTantosEquipoLocal();
        if(resultadoDTO.isGanoVisitante() != ganoVisitante){
            return true;
        }
        boolean ganoLocal = resultadoAnterior.getTantosEquipoLocal() > resultadoAnterior.getTantosEquipoVisitante();
        if(resultadoDTO.isGanoLocal() != ganoLocal){
            return true;
        }
        return false;
    }

    private void cargarResultadoDto(ResultadoFinalDTO resultadoDTO) {
        resultadoDTO.setIdCompetencia(idCompetencia);
        resultadoDTO.setIdPartido(idPartidoClickeado);
        resultadoDTO.setSePresentoLocal(sePresentoLocalCheckBox.isSelected());
        resultadoDTO.setSePresentoVisitante(sePresentoVisitanteCheckBox.isSelected());
        resultadoDTO.setGanoLocal(ganoLocalRadioButton.isSelected());
        resultadoDTO.setGanoVisitante(ganoVisitanteRadioButton.isSelected());
        if(empateRadioButton.isVisible())
            resultadoDTO.setEmpate(empateRadioButton.isSelected());
    }

    private boolean validar() {
        if(!sePresentoLocalCheckBox.isSelected() && !sePresentoVisitanteCheckBox.isSelected()){
            errorLabel.setText("Al menos un participante debe haberse presentado");
            errorLabel.setVisible(true);
            return false;
        }
        else{
            if(ganadorToggleGroup.getSelectedToggle() == null){
                errorLabel.setText("Debe seleccionar un resultado");
                errorLabel.setVisible(true);
                return false;
            }
        }
        errorLabel.setVisible(false);
        return true;
    }

    public void checkBoxClicked(ActionEvent actionEvent){
        habilitarRadiosCorrespondientes();
        if(ganadorToggleGroup.getSelectedToggle() != null) ganadorToggleGroup.getSelectedToggle().setSelected(false);
    }

    private void habilitarRadiosCorrespondientes() {
        ganoLocalRadioButton.setDisable(!sePresentoLocalCheckBox.isSelected());
        ganoVisitanteRadioButton.setDisable(!sePresentoVisitanteCheckBox.isSelected());
        if(aceptaEmpates && sePresentoLocalCheckBox.isSelected() && sePresentoVisitanteCheckBox.isSelected()){
            empateRadioButton.setDisable(false);
        }
        else{
            empateRadioButton.setDisable(true);
        }
    }

}
