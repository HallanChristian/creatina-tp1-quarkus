package br.unitins.tp1.creatina.service.municipio;

import java.util.List;

import br.unitins.tp1.creatina.dto.municipio.MunicipioRequestDTO;
import br.unitins.tp1.creatina.model.Municipio;


public interface MunicipioService {

    Municipio findById(Long id);

    List<Municipio> findByNome(String nome);

    List<Municipio> findAll();

    Municipio create(MunicipioRequestDTO dto);

    Municipio update(Long id, MunicipioRequestDTO dto);

    void delete(Long id); 
    
}
