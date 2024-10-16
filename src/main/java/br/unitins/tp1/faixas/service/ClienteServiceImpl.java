package br.unitins.tp1.faixas.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.faixas.dto.ClienteRequestDTO;
import br.unitins.tp1.faixas.dto.EnderecoRequestDTO;
import br.unitins.tp1.faixas.model.Cliente;
import br.unitins.tp1.faixas.model.Endereco;
import br.unitins.tp1.faixas.repository.ClienteRepository;
import br.unitins.tp1.faixas.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public EnderecoRepository enderecoRepository;

    @Inject
    public EnderecoServiceImpl enderecoServiceImpl;

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll().list();
    }

    @Override
    @Transactional
    public Cliente create(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setEmail(dto.email());

        clienteRepository.persist(cliente);

        cliente.setEnderecos(new ArrayList<>());

        for (EnderecoRequestDTO enderecoDTO : dto.enderecos()) {
            Endereco endereco = enderecoServiceImpl.create(enderecoDTO, cliente.getId());
            cliente.getEnderecos().add(endereco);
        }

        clienteRepository.persist(cliente);

        return cliente;
    }

    @Override
    @Transactional
    public void addEndereco(Long clienteId, EnderecoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId);
        if (cliente != null) {
            Endereco endereco = new Endereco();
            endereco.setCep(dto.cep());
            endereco.setCidade(dto.cidade());
            endereco.setEstado(dto.estado());
            endereco.setLogradouro(dto.logradouro());
            endereco.setNumero(dto.numero());
            endereco.setIdCliente(clienteId);

            enderecoRepository.persist(endereco);
            cliente.getEnderecos().add(endereco);
        }
    }
    @Override
    @Transactional
    public Cliente update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setEmail(dto.email());
        cliente.getEnderecos().clear();

        cliente.setEnderecos(new ArrayList<>());

        for (EnderecoRequestDTO enderecoDTO : dto.enderecos()) {
            Endereco endereco = enderecoServiceImpl.create(enderecoDTO, cliente.getId());
            cliente.getEnderecos().add(endereco);
        }

        clienteRepository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        enderecoRepository.deleteClienteEndereco(id);
        enderecoRepository.deleteByCliente(id);
        clienteRepository.deleteById(id);
    }
    
}
