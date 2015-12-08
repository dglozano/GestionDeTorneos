package dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIego on 8/12/2015..
 */
public class FechaDTO {

    List<PartidoDTO> partidoDTOList = new ArrayList<PartidoDTO>();
    int numeroFecha;

    public int getNumeroFecha() {
        return numeroFecha;
    }

    public void setNumeroFecha(int numeroFecha) {
        this.numeroFecha = numeroFecha;
    }

    public FechaDTO() {
    }

    public List<PartidoDTO> getPartidosDTO() {
        return partidoDTOList;
    }

    public void setPartidosDTO(List<PartidoDTO> partidoDTOList) {
        this.partidoDTOList = partidoDTOList;
    }

    public void addPartidoDto(PartidoDTO partidoDTO){
        this.partidoDTOList.add(partidoDTO);
    }
}
