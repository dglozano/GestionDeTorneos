package app;

import controllers.general.PrincipalController;
import dao.util.MiEntityManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
    public static String vista0ID = "preloader";
    public static String vista0Archivo = "preloader.fxml";
    public static String vista1ID = "misCompetencias";
    public static String vista1Archivo = "misCompetencias.fxml";
    public static String vista2ID = "crearCompetencia";
    public static String vista2Archivo = "crearCompetencia.fxml";
    public static String vista3ID = "crearCompetencia2";
    public static String vista3Archivo = "crearCompetencia2.fxml";

    @Override
    public void start(Stage primaryStage) {
        // Cargamos los recursos necesarios
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-BoldItalic.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Regular.ttf").toExternalForm(), 10);

        PrincipalController mainContainer = new PrincipalController();
        mainContainer.loadScreen(Main.vista0ID, Main.vista0Archivo);
        mainContainer.loadScreen(Main.vista1ID, Main.vista1Archivo);
        mainContainer.loadScreen(Main.vista2ID, Main.vista2Archivo);
        mainContainer.loadScreen(Main.vista3ID, Main.vista3Archivo);

        mainContainer.setScreen(Main.vista0ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                MiEntityManager.close();
                Platform.exit();
                System.exit(0);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    /**
     * El main() es ignorado en una app JavaFX.
     * Es un fallback en caso de que no se ejecute correctamente.
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}