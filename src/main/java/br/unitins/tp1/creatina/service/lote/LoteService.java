package br.unitins.tp1.creatina.service.lote;

import java.util.List;

import br.unitins.tp1.creatina.dto.lote.LoteRequestDTO;
import br.unitins.tp1.creatina.model.Lote;


public interface LoteService {

    Lote findById(Long id);

    Lote findByCodigo(String codigo);

    Lote findByIdCreatina(Long idCreatina);

    List<Lote> findAll();

    Lote create(LoteRequestDTO dto);

    Integer findEstoqueTotalPorCreatina(Long idCreatina);

    Lote update(Long id, LoteRequestDTO dto);

    void delete(Long id); 
    
}
