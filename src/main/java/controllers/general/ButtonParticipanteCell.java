package controllers.general;

import app.Main;
import controllers.listarParticipantesController;
import controllers.misCompetenciasController;
import dtos.CompetenciaDTO;
import dtos.ParticipanteDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;

public class ButtonParticipanteCell extends TableCell<ParticipanteDTO, Boolean> {
    public Button participanteTableButton;
    final StackPane paddedButton = new StackPane();

    public ButtonParticipanteCell(String nombreButton, listarParticipantesController controlador) {
        participanteTableButton= new Button(nombreButton);
        paddedButton.setPadding(new Insets(3));
        paddedButton.getChildren().add(participanteTableButton);

        participanteTableButton.getStyleClass().add("btn");
        participanteTableButton.getStyleClass().add("btn-small");
        participanteTableButton.getStyleClass().add("btn-alt");

        participanteTableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                controlador.mostrarPopUp("Esta funcionalidad esta en desarrollo","desarrollo");
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
