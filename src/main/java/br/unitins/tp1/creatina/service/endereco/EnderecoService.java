package br.unitins.tp1.creatina.service.endereco;

import java.util.List;

import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.model.Endereco;

public interface EnderecoService {

    Endereco findById(Long id);

    List<Endereco> findByCliente(Long id);

    List<Endereco> findAll();

    Endereco create(EnderecoRequestDTO dto, Long idCliente);

    Endereco update(Long id, EnderecoRequestDTO dto);

    void delete(Long id); 

    void deleteByCliente(Long idCliente);
    
}
