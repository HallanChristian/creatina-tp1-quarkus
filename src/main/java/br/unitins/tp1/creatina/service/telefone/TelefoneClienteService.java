package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneCliente;


public interface TelefoneClienteService {

    TelefoneCliente findById(Long id);

    List<TelefoneCliente> findByCliente(Long id);

    List<TelefoneCliente> findAll();

    TelefoneCliente create(Long idCliente, TelefoneClienteRequestDTO dto);

    TelefoneCliente update(Long id, TelefoneClienteRequestDTO dto);

    void delete(Long id); 

    void deleteAllByClienteId(Long idCliente);
    
}
