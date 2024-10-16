package br.unitins.tp1.faixas.service;

import java.util.List;

import br.unitins.tp1.faixas.dto.EnderecoRequestDTO;
import br.unitins.tp1.faixas.model.Endereco;


public interface EnderecoService {

    Endereco findById(Long id);

    List<Endereco> findByCliente(Long idCliente);

    List<Endereco> findAll();

    Endereco create(EnderecoRequestDTO dto, Long idCliente);

    Endereco update(Long id, EnderecoRequestDTO dto);

    void delete(Long id); 

    void deleteByCliente(Long idCliente);
    
}
