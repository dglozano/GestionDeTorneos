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
    public static String vistaPreloaderId = "preloader";
    public static String vistaPreloaderArchivo = "preloader.fxml";
    public static String vistaMisCompetenciasId = "misCompetencias";
    public static String vistaMisCompetenciasArchivo = "misCompetencias.fxml";
    public static String vistaCrearCompetenciaPasoUnoId = "crearCompetencia";
    public static String vistaCrearCompetenciaPasoUnoArchivo = "crearCompetencia.fxml";
    public static String vistaCrearCompetenciaPasoDosId = "crearCompetencia2";
    public static String vistaCrearCompetenciaPasoDosArchivo = "crearCompetencia2.fxml";
    public static String vistaTablaPosicionesId = "tablaPosiciones";
    public static String vistaTablaPosicionesArchivo = "tablaPosiciones.fxml";
    public static String vistaVerCompetenciaId="verCompetencia";
    public static String vistaVerCompetenciaArchivo = "verCompetencia.fxml";
    public static String vistaListarParticipantesId="listarParticipantes";
    public static String vistaListarParticipantesArchivo = "listarParticipantes.fxml";
    public static String vistaAltaParticipanteId="altaParticipante";
    public static String vistaAltaParticipanteArchivo = "altaParticipante.fxml";
    public static String vistaMostrarFixtureId="mostrarFixture";
    public static String vistaMostrarFixtureArchivo = "mostrarFixtureLiga.fxml";

    @Override
    public void start(Stage primaryStage) {
        // Cargamos los recursos necesarios
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-BoldItalic.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Bold.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Regular.ttf").toExternalForm(), 10);

        PrincipalController mainContainer = new PrincipalController();
        mainContainer.loadScreen(Main.vistaPreloaderId, Main.vistaPreloaderArchivo);
        mainContainer.loadScreen(Main.vistaMisCompetenciasId, Main.vistaMisCompetenciasArchivo);
        mainContainer.loadScreen(Main.vistaCrearCompetenciaPasoUnoId, Main.vistaCrearCompetenciaPasoUnoArchivo);
        mainContainer.loadScreen(Main.vistaCrearCompetenciaPasoDosId, Main.vistaCrearCompetenciaPasoDosArchivo);
        mainContainer.loadScreen(Main.vistaTablaPosicionesId, Main.vistaTablaPosicionesArchivo);
        mainContainer.loadScreen(Main.vistaVerCompetenciaId,Main.vistaVerCompetenciaArchivo);
        mainContainer.loadScreen(Main.vistaListarParticipantesId,Main.vistaListarParticipantesArchivo);
        mainContainer.loadScreen(Main.vistaAltaParticipanteId,Main.vistaAltaParticipanteArchivo);
        mainContainer.loadScreen(Main.vistaMostrarFixtureId,Main.vistaMostrarFixtureArchivo);

        mainContainer.setScreen(Main.vistaPreloaderId);

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