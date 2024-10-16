package br.unitins.tp1.faixas.service;

import java.util.List;

import br.unitins.tp1.faixas.dto.EstadoRequestDTO;
import br.unitins.tp1.faixas.model.Estado;


public interface EstadoService {

    Estado findById(Long id);

    List<Estado> findByNome(String nome);

    List<Estado> findAll();

    Estado create(EstadoRequestDTO dto);

    Estado update(Long id, EstadoRequestDTO dto);

    void delete(Long id); 
    
}
