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
            myController.setScreen(Main.vistaMisCompetenciasId);
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
        lugar1.setNombre("CAMPO DE DEPORTES");

        LugarDeRealizacion lugar2= new LugarDeRealizacion() ;
        lugar2.setUsuario(usuario1);
        lugar2.setNombre("CRAI");

        LugarDeRealizacion lugar3= new LugarDeRealizacion() ;
        lugar3.setUsuario(usuario1);
        lugar3.setNombre("CANTONA");

        LugarDeRealizacion lugar4= new LugarDeRealizacion() ;
        lugar4.setUsuario(usuario1);
        lugar4.setNombre("MARADO");

        LugarDeRealizacion lugar5= new LugarDeRealizacion() ;
        lugar5.setUsuario(usuario1);
        lugar5.setNombre("SEVEN");

        LugarDeRealizacion lugar6= new LugarDeRealizacion() ;
        lugar6.setUsuario(usuario1);
        lugar6.setNombre("IL CALCIO");

        LugarDeRealizacion lugar7= new LugarDeRealizacion() ;
        lugar7.setUsuario(usuario1);
        lugar7.setNombre("EL UNIAZO");

        LugarDeRealizacion lugar8= new LugarDeRealizacion() ;
        lugar8.setUsuario(usuario1);
        lugar8.setNombre("MARANGONI");

        rugby.addLugarDeRealizacion(lugar1);
        rugby.addLugarDeRealizacion(lugar2);
        futbol.addLugarDeRealizacion(lugar1);
        futbol.addLugarDeRealizacion(lugar3);
        futbol.addLugarDeRealizacion(lugar4);
        futbol.addLugarDeRealizacion(lugar5);
        futbol.addLugarDeRealizacion(lugar6);
        futbol.addLugarDeRealizacion(lugar7);
        futbol.addLugarDeRealizacion(lugar8);
        lugar1.addDeporte(rugby);
        lugar2.addDeporte(rugby);
        lugar1.addDeporte(futbol);
        lugar3.addDeporte(futbol);
        lugar4.addDeporte(futbol);
        lugar5.addDeporte(futbol);
        lugar6.addDeporte(futbol);
        lugar7.addDeporte(futbol);
        lugar8.addDeporte(futbol);

        lugarDeRealizacionDao.crearLugar(lugar1);
        lugarDeRealizacionDao.crearLugar(lugar2);
        lugarDeRealizacionDao.crearLugar(lugar3);
        lugarDeRealizacionDao.crearLugar(lugar4);
        lugarDeRealizacionDao.crearLugar(lugar5);
        lugarDeRealizacionDao.crearLugar(lugar6);
        lugarDeRealizacionDao.crearLugar(lugar7);
        lugarDeRealizacionDao.crearLugar(lugar8);
        deporteDao.crearDeporte(rugby);
        deporteDao.crearDeporte(futbol);
        deporteDao.crearDeporte(tenis);

        Competencia competencia1= new Competencia();
        competencia1.setUsuario(usuario1);
        competencia1.setNombre("TORNEO DOS ORILLAS");
        competencia1.setModalidad(Modalidad.LIGA);
        competencia1.setEstado(Estado.CREADA);
        competencia1.setSistemaPuntuacion(SistemaPuntuacion.SET);
        competencia1.setAceptaEmpate(false);
        competencia1.setDeporte(rugby);
        competenciaDAO.crearCompetencia(competencia1);

        Competencia competencia2= new Competencia();
        competencia2.setUsuario(usuario1);
        competencia2.setNombre("LIGA SANTAFESINA");
        competencia2.setModalidad(Modalidad.LIGA);
        competencia2.setEstado(Estado.EN_DISPUTA);
        competencia2.setSistemaPuntuacion(SistemaPuntuacion.RESULTADO_FINAL);
        competencia2.setAceptaEmpate(true);
        competencia2.setDeporte(futbol);
        competenciaDAO.crearCompetencia(competencia2);

        Usuario usuario2 = new Usuario();
        usuarioDao.crearUsuario(usuario2);
        Competencia competencia3= new Competencia();
        competencia3.setUsuario(usuario1);
        competencia3.setNombre("TORNEO AFA");
        competencia3.setModalidad(Modalidad.LIGA);
        competencia3.setSistemaPuntuacion(SistemaPuntuacion.PUNTUACION);
        competencia3.setAceptaEmpate(true);
        competencia3.setEstado(Estado.FINALIZADA);
        competencia3.setDeporte(futbol);
        competenciaDAO.crearCompetencia(competencia3);

    }
}
