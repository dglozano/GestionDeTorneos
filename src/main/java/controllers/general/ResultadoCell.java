package controllers.general;

import controllers.mostrarFixtureController;
import dtos.PartidoDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.layout.StackPane;

public class ResultadoCell extends TableCell<PartidoDTO, Boolean> {
    public Button verButton = new Button("Cargar");
    final StackPane paddedButton = new StackPane();

    public ResultadoCell(mostrarFixtureController controlador) {
        paddedButton.setPadding(new Insets(3));
        paddedButton.getChildren().add(verButton);

        verButton.getStyleClass().add("btn");
        verButton.getStyleClass().add("btn-small");
        verButton.getStyleClass().add("btn-alt");

        verButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                int idPartidoClickeado =((PartidoDTO)getTableView().getItems().get(getIndex())).getId();
                String sistemaPuntuacion = controlador.getSistemaCompetencia();
                controlador.setIdPartidoClickeado(idPartidoClickeado);
                controlador.mostrarPopUpResultado(sistemaPuntuacion);
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
