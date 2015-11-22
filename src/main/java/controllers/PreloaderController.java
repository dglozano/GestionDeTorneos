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
    public void inicializar(String mensaje) {inicializar();};
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

        //usuarioLogueado.setUsuarioLogueado(usuarioDao.buscarUsuarioPorId(1));

        CompetenciaDao competenciaDAO = CompetenciaDao.getInstance();
        DeporteDao deporteDao = DeporteDao.getInstance();
        UsuarioDao usuarioDao = UsuarioDao.getInstance();
        LugarDeRealizacionDao lugarDeRealizacionDao = LugarDeRealizacionDao.getInstance();
        usuarioDao.crearUsuario(usuario1);

        Participante p1 = new Participante();
        Participante p2 = new Participante();
        Participante p3 = new Participante();
        Participante p4 = new Participante();
        Participante p5 = new Participante();

        p1.setNombre("Diegol");
        p1.setEmail("dglozano95@live.com");

        p2.setNombre("Juanchera");
        p2.setEmail("juan@flioh.com");

        p3.setNombre("Kevinchera");
        p3.setEmail("kevin@panda.com");

        p4.setNombre("Augusto");
        p4.setEmail("augusto@yuyo.com");

        p5.setNombre("Camila");
        p5.setEmail("cami@tibalt.com");

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

        Disponibilidad d1= new Disponibilidad();
        Disponibilidad d2= new Disponibilidad();
        Disponibilidad d3= new Disponibilidad();
        d1.setLugarDeRealizacion(lugar1);
        d1.setDisponibilidad(2);
        d2.setDisponibilidad(1);
        d3.setDisponibilidad(4);
        d1.setLugarDeRealizacion(lugar1);
        d2.setLugarDeRealizacion(lugar3);
        d3.setLugarDeRealizacion(lugar4);


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
        competencia1.setModalidad(Modalidad.ELIM_DOBLE);
        competencia1.setEstado(Estado.EN_DISPUTA);
        competencia1.setSistemaPuntuacion(SistemaPuntuacion.SET);
        competencia1.setAceptaEmpate(false);
        competencia1.setDeporte(rugby);
        competenciaDAO.crearCompetencia(competencia1);

        Competencia competencia2= new Competencia();
        competencia2.addParticipante(p1);
        competencia2.addParticipante(p2);
        competencia2.addParticipante(p3);
        competencia2.addParticipante(p4);
        competencia2.addParticipante(p5);
        competencia2.addDisponibilidad(d1);
        competencia2.addDisponibilidad(d2);
        competencia2.addDisponibilidad(d3);
        competencia2.setUsuario(usuario1);
        competencia2.setNombre("LIGA SANTAFESINA");
        competencia2.setModalidad(Modalidad.LIGA);
        competencia2.setEstado(Estado.CREADA);
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
