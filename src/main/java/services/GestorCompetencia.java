package services;
import dao.CompetenciaDao;
import models.Competencia;
import models.UsuarioLogueado;
import models.dtos.CompetenciaDTO;

import java.util.ArrayList;
import java.util.List;

public class GestorCompetencia {
    private CompetenciaDao competenciaDao = CompetenciaDao.getInstance();
    private UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstance();

    public void nuevaCompetencia(Competencia c) {
        competenciaDao.crearCompetencia(c);
    }

    public List<CompetenciaDTO> listarTodasMisCompetencias(){
        int idUsuarioLogueado = usuarioLogueado.getUsuarioLogueado().getId();
        List<Competencia> competenciasUsuario = competenciaDao.buscarTodasCompetencias(idUsuarioLogueado);
        List<CompetenciaDTO> listaCompetenciasDTO= new ArrayList<CompetenciaDTO>();
        for(Competencia comp: competenciasUsuario){
            int id = comp.getId();
            String nombre = comp.getNombre();
            String estado = comp.getEstado().getEstadoString();
            String modalidad = comp.getModalidad().getModalidadString();
            String deporte = comp.getDeporte().getNombre();
            CompetenciaDTO unaCompetencia = new CompetenciaDTO(id,nombre,deporte,estado,modalidad);
            listaCompetenciasDTO.add(unaCompetencia);
        }
        /* TODO 02: Descomentar cuando pueda cargar datos en la tabla de la vista */
        for(CompetenciaDTO compdto: listaCompetenciasDTO){
            System.out.println("Id: "+compdto.getId());
            System.out.println("Nombre: "+compdto.getNombre());
            System.out.println("Deporte: "+compdto.getDeporte());
            System.out.println("Modalidad: "+compdto.getModalidad());
            System.out.println("Estado: "+compdto.getEstado() + "\n");
        }
        return listaCompetenciasDTO;
    }
}
