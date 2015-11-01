package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;

public class crearCompetenciasController implements ControlledScreen {

    private PrincipalController myController;
    private Stage modal;
    private Parent parent;

    @FXML private Label errorPuntuacion;
    @FXML private ToggleGroup modalidad;

    @FXML private CheckComboBox<String> lugaresCombo;

    @FXML private Button okButton;


    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        // TODO: Acomodar esto mejor, simplemente estaba viendo que ande.
        final ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i <= 4; i++) {
            strings.add("Item " + i);
        }
        lugaresCombo.getItems().addAll(strings);
        lugaresCombo.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                System.out.println(lugaresCombo.getCheckModel().getCheckedItems());
            }
        });
    }

    public void validaciones(){
        if (modalidad.getSelectedToggle() == null){
            errorPuntuacion.setText("Debe completar este campo para continuar.");
            errorPuntuacion.setVisible(true);
        } else{
            errorPuntuacion.setVisible(false);
            mostrarPopupExito();
        }
    }

    private void mostrarPopupExito(){
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/popupCompetenciaCreada.fxml"));
        try {
            parent = loader.load();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
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

    public void irMisCompetencias(ActionEvent actionEvent) {
        myController.setScreen(Main.vista1ID);
    }

    public void continuar(ActionEvent actionEvent) {
        validaciones();
    }

    public void close(ActionEvent actionEvent){
        Stage modal = (Stage)okButton.getScene().getWindow();
        modal.close();
    }

}
