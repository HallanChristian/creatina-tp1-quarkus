package br.unitins.tp1.creatina.service.estado;

import java.util.List;

import br.unitins.tp1.creatina.dto.EstadoRequestDTO;
import br.unitins.tp1.creatina.model.Estado;


public interface EstadoService {

    Estado findById(Long id);

    List<Estado> findByNome(String nome);

    List<Estado> findAll();

    Estado create(EstadoRequestDTO dto);

    Estado update(Long id, EstadoRequestDTO dto);

    void delete(Long id); 
    
}
