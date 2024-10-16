package br.unitins.tp1.faixas.service;

import java.util.List;

import br.unitins.tp1.faixas.dto.CreatinaRequestDTO;
import br.unitins.tp1.faixas.model.Creatina;


public interface CreatinaService {

    Creatina findById(Long id);

    List<Creatina> findByNome(String nome);

    List<Creatina> findAll();

    Creatina create(CreatinaRequestDTO dto);

    Creatina update(Long id, CreatinaRequestDTO dto);

    void delete(Long id); 
    
}
