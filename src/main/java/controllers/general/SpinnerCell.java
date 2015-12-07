package controllers.general;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;

public class SpinnerCell<DisponibilidadLugar, T> extends TableCell<DisponibilidadLugar, T> {

    private Spinner<T> spinner;

    public SpinnerCell() {
        this(1);
    }

    public SpinnerCell(int step) {
        this.spinner = new Spinner<T>(1, 100, step);
        spinner.setFocusTraversable(false);
        spinner.valueProperty().addListener(new ChangeListener<T>() {
            public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                ((dtos.DisponibilidadLugar)getTableView().getItems().get(getIndex())).setDisponibilidad((Integer)newValue);
            }
        });
        setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            setGraphic(this.spinner);
        }
    }

}