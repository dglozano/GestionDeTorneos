package controllers.general;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.util.HashMap;

public class PrincipalController extends StackPane {
    //Contiene todas las vistas
    private HashMap<String, Node> screens = new HashMap<>();
    private HashMap<String, ControlledScreen> screensController = new HashMap<>();

    public PrincipalController() {
        super();
    }

    // Agregar la vista a la colección
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    // Retorna el nodo con el nombre correspondiente
    public Node getScreen(String name) {
        return screens.get(name);
    }

    // Carga el FXML, agrega la vista a la colección y injecta el Pane en el controlador
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/"+resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            screensController.put(name, myScreenControler);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Setea la vista, con validaciones en caso de que no esté cargado el FXML
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(350), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);                    //remove the displayed screen
                                getChildren().add(0, screens.get(name));     //add the screen
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }

            ControlledScreen myScreenControler = screensController.get(name);
            myScreenControler.inicializar();
            System.out.println((myScreenControler.toString()));
            System.out.println("DEBERIA INICIALIZAR DIEGO RE PUTO");

            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

    // Elimina la vista de la colección
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}

