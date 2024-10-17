package br.unitins.tp1.faixas.service;

import java.util.List;

import br.unitins.tp1.faixas.dto.FornecedorRequestDTO;
import br.unitins.tp1.faixas.model.Fornecedor;


public interface FornecedorService {

    Fornecedor findById(Long id);

    List<Fornecedor> findByNome(String nome);

    List<Fornecedor> findByCnpj(String cnpj);

    List<Fornecedor> findAll();

    Fornecedor create(FornecedorRequestDTO dto);

    Fornecedor update(Long id, FornecedorRequestDTO dto);

    void delete(Long id); 
    
}
