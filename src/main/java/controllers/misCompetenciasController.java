package controllers;

import app.Main;
import controllers.general.ControlledScreen;
import controllers.general.PrincipalController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import dtos.CompetenciaDTO;
import services.GestorCompetencia;
import services.GestorDeporte;

import java.util.List;

public class misCompetenciasController implements ControlledScreen {

    private PrincipalController myController;
    private GestorCompetencia gestorCompetencia;
    private GestorDeporte gestorDeporte;

    @FXML private TextField nombreCompetenciaTextField;
    @FXML private ToggleGroup modalidadToggleGroup;
    @FXML private ComboBox<String> deportesComboBox;

    public void setScreenParent(PrincipalController screenParent){
        myController = screenParent;
    }

    public void inicializar(){
        this.gestorCompetencia = new GestorCompetencia();
        this.gestorDeporte = new GestorDeporte();
        List<CompetenciaDTO> listaCompetencias = gestorCompetencia.listarTodasMisCompetencias();
        /* TODO 02: Descomentar cuando pueda cargar datos en la tabla de la vista
        for(CompetenciaDTO compdto: listaCompetencias){
            System.out.println("Id: "+compdto.getId());
            System.out.println("Nombre: "+compdto.getNombre());
            System.out.println("Deporte: "+compdto.getDeporte());
            System.out.println("Modalidad: "+compdto.getModalidad());
            System.out.println("Estado: "+compdto.getEstado() + "\n");
        }*/
        cargarDeportes();
    }

    private void cargarDeportes() {
        List<String> listaDeportes = gestorDeporte.listarDeportes();
        for(String deporte: listaDeportes){
            deportesComboBox.getItems().add(deporte);
        }
    }

    public void irCrearCompetencia(ActionEvent actionEvent) {
        myController.setScreen(Main.vista2ID);
    }

    public void filtrarCompetencias(ActionEvent actionEvent){
        List<CompetenciaDTO> listaCompetenciasFiltradas = gestorCompetencia.listarTodasMisCompetencias();
        /* TODO 02: Descomentar cuando pueda cargar datos en la tabla de la vista */
        for(CompetenciaDTO compdto: listaCompetenciasFiltradas) {
            System.out.println("Id: " + compdto.getId());
            System.out.println("Nombre: " + compdto.getNombre());
            System.out.println("Deporte: " + compdto.getDeporte());
            System.out.println("Modalidad: " + compdto.getModalidad());
            System.out.println("Estado: " + compdto.getEstado() + "\n");
        }
    }

}
