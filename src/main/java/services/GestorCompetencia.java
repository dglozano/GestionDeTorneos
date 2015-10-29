package services;

import dao.CompetenciaDao;
import models.Competencia;

import java.util.List;

public class GestorCompetencia {
    private CompetenciaDao competenciaDao = CompetenciaDao.getInstance();

    public void nuevaCompetencia(Competencia c) {
        competenciaDao.crearCompetencia(c);
    }
}
