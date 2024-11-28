package br.unitins.tp1.creatina.service.fornecedor;

import java.util.List;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;


public interface FornecedorService {

    Fornecedor findById(Long id);

    List<Fornecedor> findByNome(String nome);

    List<Fornecedor> findByCnpj(String cnpj);

    List<Fornecedor> findAll();

    Fornecedor create(FornecedorRequestDTO dto);

    void addTelefone(Long fornecedorId, TelefoneFornecedorRequestDTO dto);

    Fornecedor update(Long id, FornecedorRequestDTO dto);

    void delete(Long id); 
    
}
