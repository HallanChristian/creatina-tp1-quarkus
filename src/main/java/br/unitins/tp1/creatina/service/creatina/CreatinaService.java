package br.unitins.tp1.creatina.service.creatina;

import java.util.List;

import br.unitins.tp1.creatina.dto.CreatinaRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;


public interface CreatinaService {

    Creatina findById(Long id);

    List<Creatina> findByNome(String nome);

    List<Creatina> findAll();

    Creatina create(CreatinaRequestDTO dto);

    Creatina update(Long id, CreatinaRequestDTO dto);

    void delete(Long id); 
    
}
