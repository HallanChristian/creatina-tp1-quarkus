package br.unitins.tp1.creatina.service.cliente;

import java.util.List;

import br.unitins.tp1.creatina.dto.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;


public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

    List<Cliente> findByCpf(String cpf);

    List<Cliente> findAll();

    Cliente create(ClienteRequestDTO dto);

    void addEndereco(Long clienteId, EnderecoRequestDTO dto);
    
    void addTelefone(Long clienteId, TelefoneClienteRequestDTO dto);

    Cliente update(Long id, ClienteRequestDTO dto);

    void delete(Long id); 
    
}
