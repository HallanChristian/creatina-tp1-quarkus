package br.unitins.tp1.creatina.service.fornecedor;

import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;


public interface FornecedorService {

    Fornecedor findById(Long id);

    List<Fornecedor> findByNome(String nome);

    Fornecedor findByCnpj(String cnpj);

    List<Fornecedor> findAll();

    Fornecedor create(FornecedorRequestDTO dto);

    void addTelefone(Long fornecedorId, TelefoneRequestDTO dto);

    void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto);

    void addEndereco(Long fornecedorId, EnderecoRequestDTO dto);

    void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto);

    Fornecedor update(Long id, FornecedorRequestDTO dto);

    void delete(Long id); 
    
}
