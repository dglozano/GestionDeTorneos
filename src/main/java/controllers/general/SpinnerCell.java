package controllers.general;

import dtos.DisponibilidadLugar;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;

/**
 * Created by DIego on 4/11/2015..
 */
public class SpinnerCell<DisponibilidadLugar, Integer> extends TableCell<DisponibilidadLugar, Integer> {

    private Spinner<Integer> spinner;

    public SpinnerCell() {
        this(1);
    }

    public SpinnerCell(int step) {
        this.spinner = new Spinner<>(1, 100, step);
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                commitEdit(newValue);
                updateItem(newValue,false);
            }
        });
        setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
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