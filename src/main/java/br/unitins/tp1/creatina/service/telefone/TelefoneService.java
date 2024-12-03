package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Telefone;


public interface TelefoneService {

    Telefone findById(Long id);

    List<Telefone> findByCliente(Long id);

    List<Telefone> findByFuncionario(Long id);

    List<Telefone> findByFornecedor(Long id);

    List<Telefone> findAll();

    Telefone create(Long idCliente, TelefoneRequestDTO dto);

    Telefone update(Long id, TelefoneRequestDTO dto);

    void delete(Long id); 

    void deleteTelefoneByCliente(Long idCliente, Long idTelefone);

    void deleteTelefoneByFornecedor(Long idFornecedor, Long idTelefone);

    void deleteTelefoneByFuncionario(Long idFuncionario, Long idTelefone);

    void deleteAllByClienteId(Long idCliente);

    void deleteAllByFuncionarioId(Long idFuncionario);

    void deleteAllByFornecedorId(Long idFornecedor);
    
}
