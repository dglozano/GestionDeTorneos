package services;

import dao.CompetenciaDao;
import models.Competencia;

public class GestorCompetencia {
    private CompetenciaDao dao = new CompetenciaDao();

    public void nuevaCompetencia(Competencia c) {
        dao.crear(c);
    }
}
