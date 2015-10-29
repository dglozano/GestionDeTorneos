package app;

import dao.util.MiEntityManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

    private Stage stage;
    private static Main instance;

    public Main(){
        instance = this;
    }

    public static Main getInstance(){
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getClassLoader().getResource("fonts/OpenSans-Regular.ttf").toExternalForm(), 10);

        primaryStage.setTitle("Gestion De Torneos");
        primaryStage.setResizable(false);

        stage = primaryStage;

        Runnable levantarBD = () -> {
            MiEntityManager.get();
        };
        new Thread(levantarBD).start();

        goToLoader();
        primaryStage.show();
    }

    private void goToLoader(){
        try {
            replaceSceneContent("preloader.fxml");
            goToMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToMain(){
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(3500),
                ae -> {
                    try {
                        replaceSceneContent("main.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
        timeline.play();
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource("fmxl/" + fxml));
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 1000, 600);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

    public static void main(String[] args) {
        launch(args);
    }
}