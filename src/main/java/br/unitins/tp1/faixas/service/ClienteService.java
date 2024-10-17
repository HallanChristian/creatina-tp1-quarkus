package br.unitins.tp1.faixas.service;

import java.util.List;

import br.unitins.tp1.faixas.dto.ClienteRequestDTO;
import br.unitins.tp1.faixas.dto.EnderecoRequestDTO;
import br.unitins.tp1.faixas.model.Cliente;


public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

     List<Cliente> findByCpf(String cpf);

    List<Cliente> findAll();

    Cliente create(ClienteRequestDTO dto);

    void addEndereco(Long clienteId, EnderecoRequestDTO dto);

    Cliente update(Long id, ClienteRequestDTO dto);

    void delete(Long id); 
    
}
