package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Telefone;


public interface TelefoneService {

    Telefone findById(Long id);

    List<Telefone> findByCliente(Long id);

    List<Telefone> findByFuncionario(Long id);

    List<Telefone> findByFornecedor(Long id);

    List<Telefone> findByNumero(String numero);

    List<Telefone> findByDdd(String ddd);

    List<Telefone> findAll();

    Telefone update(Long id, TelefoneRequestDTO dto);

    void delete(Long id); 
    
}
