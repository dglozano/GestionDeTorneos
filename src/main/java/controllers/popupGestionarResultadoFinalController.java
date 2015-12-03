package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.ResultadoFinalDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Partido;
import services.GestorCompetencia;
import services.GestorResultado;

/**
 * Created by DIego on 21/11/2015..
 */
public class popupGestionarResultadoFinalController implements ControlledScreen {
    private PrincipalController myController;
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

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        partido = gestorCompetencia.buscarPartidoPorId(idPartidoClickeado);
        aceptaEmpates = gestorCompetencia.buscarCompetenciaPorId(idCompetencia).isAceptaEmpate();
        sePresentoLocalCheckBox.setSelected(false);
        sePresentoVisitanteCheckBox.setSelected(false);
        sePresentoLocalCheckBox.setText(partido.getLocal().getNombre());
        sePresentoVisitanteCheckBox.setText(partido.getVisitante().getNombre());
        ganoLocalRadioButton.setText(partido.getLocal().getNombre());
        ganoVisitanteRadioButton.setText(partido.getVisitante().getNombre());
        if(ganadorToggleGroup.getSelectedToggle() != null) ganadorToggleGroup.getSelectedToggle().setSelected(false);
        ganoLocalRadioButton.setDisable(true);
        ganoVisitanteRadioButton.setDisable(true);
        empateRadioButton.setDisable(true);
        if(aceptaEmpates) empateRadioButton.setVisible(true);
        else empateRadioButton.setVisible(false);
        errorLabel.setVisible(false);
    }

    public void inicializar(String mensaje){
        idPartidoClickeado = Integer.parseInt(mensaje);
        inicializar(); }

    public Object mensajeControladorAnterior(){ return idCompetencia; }

    public void cancelar(ActionEvent actionEvent){
        ControlledScreen anterior = myController.getControladorAnterior();
        myController.setControladorAnterior(this);
        anterior.inicializar();
        Stage modal = (Stage)cancelarButton.getScene().getWindow();
        modal.close();
    }

    public void aceptar(ActionEvent actionEvent){
        if(validar()){
            ResultadoFinalDTO resultadoDTO = new ResultadoFinalDTO();
            cargarResultadoDto(resultadoDTO);
            gestorCompetencia.cargarResultadoFinal(resultadoDTO);
            myController.getControladorAnterior().inicializar();
            myController.setControladorAnterior(this);
            Stage modal = (Stage)okButton.getScene().getWindow();
            modal.close();
        }
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
        if(((CheckBox)actionEvent.getSource()).equals(sePresentoLocalCheckBox)){
            ganoLocalRadioButton.setDisable(!sePresentoLocalCheckBox.isSelected());
        }
        else{
            ganoVisitanteRadioButton.setDisable(!sePresentoVisitanteCheckBox.isSelected());
        }
        if(aceptaEmpates && sePresentoLocalCheckBox.isSelected() && sePresentoVisitanteCheckBox.isSelected()){
            empateRadioButton.setDisable(false);
        }
        else{
            empateRadioButton.setDisable(true);
        }
        if(ganadorToggleGroup.getSelectedToggle() != null) ganadorToggleGroup.getSelectedToggle().setSelected(false);
    }

}
