package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResource("../fonts/OpenSans-Italic.ttf").toExternalForm(), 10);
        Font.loadFont(getClass().getResource("../fonts/OpenSans-Regular.ttf").toExternalForm(), 10);

        Parent root = FXMLLoader.load(getClass().getResource("../fmxl/main.fxml"));

        primaryStage.setTitle("Gestion De Torneos");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
