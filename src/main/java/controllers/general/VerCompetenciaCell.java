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
                controlador.getMyController().setScreen(Main.vistaVerCompetenciaId,controlador);
            }
        });
    }

    @Override protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(paddedButton);
        }
    }
}
