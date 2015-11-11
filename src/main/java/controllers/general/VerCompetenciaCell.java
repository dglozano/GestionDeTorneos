package controllers.general;

import app.Main;
import controllers.misCompetenciasController;
import dtos.CompetenciaDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class VerCompetenciaCell extends TableCell<CompetenciaDTO, Boolean> {
    public Button verButton = new Button("Ver mas");
    final StackPane paddedButton = new StackPane();

    private Stage modal;
    private Parent parent;

    public VerCompetenciaCell(misCompetenciasController controlador) {
        paddedButton.setPadding(new Insets(3));
        paddedButton.getChildren().add(verButton);

        verButton.getStyleClass().add("btn");
        verButton.getStyleClass().add("btn-small");
        verButton.getStyleClass().add("btn-alt");

        verButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                int idCompetenciaClickeada =((CompetenciaDTO)getTableView().getItems().get(getIndex())).getId();
                controlador.setIdCompetenciaClickeada(idCompetenciaClickeada);
                /*TODO 00: Hacer que vaya a CU20 ver competencia*/
                controlador.getMyController().setScreen(Main.vistaVerCompetenciaId,controlador);
                //mostrarComingSoon();
            }
        });
    }

    private void mostrarComingSoon(){
        final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/popupComingSoon.fxml"));
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

    @Override protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paddedButton);
        }
    }
}
