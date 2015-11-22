package controllers;

import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dtos.ResultadoFinalDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    @FXML private Button okButton;
    @FXML private Button cancelarButton;
    @FXML private Label detailsLabel;
    @FXML private ToggleGroup ganadorToggleGroup;
    @FXML private RadioButton ganoEquipo1RadioButton;
    @FXML private RadioButton ganoEquipo2RadioButton;
    @FXML private RadioButton empateRadioButton;
    @FXML private CheckBox sePresentoEquipo1CheckBox;
    @FXML private CheckBox sePresentoEquipo2CheckBox;
    @FXML private Label errorLabel;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar() {
        idCompetencia = (Integer) myController.getControladorAnterior().mensajeControladorAnterior();
        aceptaEmpates = gestorCompetencia.buscarCompetenciaPorId(idCompetencia).isAceptaEmpate();
        sePresentoEquipo1CheckBox.setSelected(false);
        sePresentoEquipo2CheckBox.setSelected(false);
        if(ganadorToggleGroup.getSelectedToggle() != null) ganadorToggleGroup.getSelectedToggle().setSelected(false);
        ganoEquipo1RadioButton.setDisable(true);
        ganoEquipo2RadioButton.setDisable(true);
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
        myController.getControladorAnterior().inicializar();
        myController.setControladorAnterior(this);
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
        resultadoDTO.setSePresentoLocal(sePresentoEquipo1CheckBox.isSelected());
        resultadoDTO.setSePresentoVisitante(sePresentoEquipo2CheckBox.isSelected());
        resultadoDTO.setGanoLocal(ganoEquipo1RadioButton.isSelected());
        resultadoDTO.setGanoVisitante(ganoEquipo2RadioButton.isSelected());
        if(empateRadioButton.isVisible())
            resultadoDTO.setEmpate(empateRadioButton.isSelected());
    }

    private boolean validar() {
        if(!sePresentoEquipo1CheckBox.isSelected() && !sePresentoEquipo2CheckBox.isSelected()){
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
        if(((CheckBox)actionEvent.getSource()).getText().equals("Equipo 1")){
            ganoEquipo1RadioButton.setDisable(!sePresentoEquipo1CheckBox.isSelected());
        }
        else{
            ganoEquipo2RadioButton.setDisable(!sePresentoEquipo2CheckBox.isSelected());
        }
        if(aceptaEmpates && sePresentoEquipo1CheckBox.isSelected() && sePresentoEquipo2CheckBox.isSelected()){
            empateRadioButton.setDisable(false);
        }
        else{
            empateRadioButton.setDisable(true);
        }
        if(ganadorToggleGroup.getSelectedToggle() != null) ganadorToggleGroup.getSelectedToggle().setSelected(false);
    }

}
