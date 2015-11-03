package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import dao.CompetenciaDao;
import dao.DeporteDao;
import dao.LugarDeRealizacionDao;
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

    public void inicializar(){};
    public Object mensajeControladorAnterior(){
        return null;
    };

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
        LugarDeRealizacionDao lugarDeRealizacionDao = LugarDeRealizacionDao.getInstance();
        usuarioDao.crearUsuario(usuario1);

        Deporte rugby = new Deporte("RUGBY");
        Deporte futbol = new Deporte ("FUTBOL");
        Deporte tenis = new Deporte("TENIS");

        LugarDeRealizacion lugar1= new LugarDeRealizacion() ;
        lugar1.setUsuario(usuario1);
        lugar1.setNombre("Campo de Deportes");

        LugarDeRealizacion lugar2= new LugarDeRealizacion() ;
        lugar2.setUsuario(usuario1);
        lugar2.setNombre("CRAI");

        LugarDeRealizacion lugar3= new LugarDeRealizacion() ;
        lugar3.setUsuario(usuario1);
        lugar3.setNombre("Cantona");

        rugby.addLugarDeRealizacion(lugar1);
        rugby.addLugarDeRealizacion(lugar2);
        futbol.addLugarDeRealizacion(lugar1);
        futbol.addLugarDeRealizacion(lugar3);
        lugar1.addDeporte(rugby);
        lugar1.addDeporte(futbol);
        lugar3.addDeporte(futbol);
        lugar2.addDeporte(rugby);
        lugarDeRealizacionDao.crearLugar(lugar1);
        lugarDeRealizacionDao.crearLugar(lugar2);
        lugarDeRealizacionDao.crearLugar(lugar3);
        deporteDao.crearDeporte(rugby);
        deporteDao.crearDeporte(futbol);
        deporteDao.crearDeporte(tenis);

        Competencia competencia1= new Competencia();
        competencia1.setUsuario(usuario1);
        competencia1.setNombre("TORNEO DOS ORILLAS");
        competencia1.setModalidad(Modalidad.ELIM_SIMPLE);
        competencia1.setEstado(Estado.CREADA);
        competencia1.setDeporte(rugby);
        competenciaDAO.crearCompetencia(competencia1);
        Competencia competencia2= new Competencia();
        competencia2.setUsuario(usuario1);
        competencia2.setNombre("LIGA SANTAFESINA");
        competencia2.setModalidad(Modalidad.ELIM_DOBLE);
        competencia2.setEstado(Estado.EN_DISPUTA);
        competencia2.setDeporte(futbol);
        competenciaDAO.crearCompetencia(competencia2);

        Usuario usuario2 = new Usuario();
        usuarioDao.crearUsuario(usuario2);
        Competencia competencia3= new Competencia();
        competencia3.setUsuario(usuario1);
        competencia3.setNombre("TORNEO AFA");
        competencia3.setModalidad(Modalidad.LIGA);
        competencia3.setEstado(Estado.FINALIZADA);
        competencia3.setDeporte(futbol);
        competenciaDAO.crearCompetencia(competencia3);

    }
}
