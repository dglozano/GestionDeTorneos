package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dao.CompetenciaDao;
import dao.DeporteDao;
import dao.UsuarioDao;
import dao.util.MiEntityManager;
import models.*;

import javax.persistence.EntityManager;

public class PreloaderController implements ControlledScreen {

    private PrincipalController myController;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
        levantarEntityManager();
    }

    public void inicializar(){}

    public void levantarEntityManager(){
        Runnable levantarBD = () -> {
            EntityManager em = MiEntityManager.get();
            seteoDatosPrueba();
            myController.setScreen(Main.vista1ID);
            em.close();
        };
        Thread hiloBD = new Thread(levantarBD);
        hiloBD.start();
    }

    private void seteoDatosPrueba(){
        // No respeta el modelo de capas. Es solo para probar, despues lo cambiamos
        UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();
        Usuario usuario1 = new Usuario();
        usuarioLogueado.setUsuarioLogueado(usuario1);
        CompetenciaDao competenciaDAO = CompetenciaDao.getInstance();
        DeporteDao deporteDao = DeporteDao.getInstance();
        UsuarioDao usuarioDao = UsuarioDao.getInstance();
        usuarioDao.crearUsuario(usuario1);
        Deporte rugby = new Deporte("rugby");
        Deporte futbol = new Deporte ("futbol");
        deporteDao.crearDeporte(rugby);
        deporteDao.crearDeporte(futbol);

        Competencia competencia1= new Competencia();
        competencia1.setUsuario(usuario1);
        competencia1.setNombre("Torneo Dos Orillas");
        competencia1.setModalidad(Modalidad.ELIM_SIMPLE);
        competencia1.setEstado(Estado.CREADA);
        competencia1.setDeporte(rugby);
        competenciaDAO.crearCompetencia(competencia1);
        Competencia competencia2= new Competencia();
        competencia2.setUsuario(usuario1);
        competencia2.setNombre("Liga Santafesina");
        competencia2.setModalidad(Modalidad.ELIM_DOBLE);
        competencia2.setEstado(Estado.EN_DISPUTA);
        competencia2.setDeporte(futbol);
        competenciaDAO.crearCompetencia(competencia2);

        Usuario usuario2 = new Usuario();
        usuarioDao.crearUsuario(usuario2);
        Competencia competencia3= new Competencia();
        competencia3.setUsuario(usuario2);
        competencia3.setNombre("Torneo AFA");
        competencia3.setModalidad(Modalidad.LIGA);
        competencia3.setEstado(Estado.FINALIZADA);
        competencia3.setDeporte(futbol);
        competenciaDAO.crearCompetencia(competencia3);

    }
}
