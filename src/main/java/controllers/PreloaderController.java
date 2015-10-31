package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dao.util.MiEntityManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PreloaderController implements ControlledScreen {

    PrincipalController myController;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
        // Demorar 4 segundos y pasar a la primer pantalla mientras ya levanta el entity manager
        levantarEntityManager();
        mostrarPrimerPantalla(4000);
    }

    public void levantarEntityManager(){
        Runnable levantarBD = () -> {
            MiEntityManager.get();
        };
        Thread hiloBD = new Thread(levantarBD);
        hiloBD.start();
    }

    public void mostrarPrimerPantalla(int espera) {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(espera),
                ae -> {
                    myController.setScreen(Main.vista1ID);
                }));
        timeline.play();
    }
}
