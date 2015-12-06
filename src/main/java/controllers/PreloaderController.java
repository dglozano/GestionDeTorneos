package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dao.UsuarioDao;
import dao.util.MiEntityManager;
import models.UsuarioLogueado;

import javax.persistence.EntityManager;

public class PreloaderController extends ControlledScreen {

    @Override
    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
        levantarEntityManager();
    }

    @Override
    public void inicializar(){};

    @Override
    public Object mensajeControladorAnterior(){
        return null;
    };

    public void levantarEntityManager(){
        Runnable levantarBD = () -> {
            EntityManager em = MiEntityManager.get();
            inicializarDatosPrueba();
            myController.setScreen(Main.vistaMisCompetenciasId);
            em.close();
        };
        Thread hiloBD = new Thread(levantarBD);
        hiloBD.start();
    }

    private void inicializarDatosPrueba(){
        UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();
        UsuarioDao usuarioDao = UsuarioDao.getInstance();
        usuarioLogueado.setUsuarioLogueado(usuarioDao.buscarUsuarioPorId(1));
    }
}
