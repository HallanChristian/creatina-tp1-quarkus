package br.unitins.tp1.creatina.service.cliente;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.TelefoneCliente;
import br.unitins.tp1.creatina.repository.cliente.ClienteRepository;
import br.unitins.tp1.creatina.repository.endereco.EnderecoRepository;
import br.unitins.tp1.creatina.repository.telefone.TelefoneClienteRepository;
import br.unitins.tp1.creatina.service.endereco.EnderecoServiceImpl;
import br.unitins.tp1.creatina.service.telefone.TelefoneClienteServiceImpl;
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

    @Inject
    public TelefoneClienteRepository  telefoneClienteRepository;

    @Inject
    public TelefoneClienteServiceImpl  telefoneClienteServiceImpl;

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    @Override
    public List<Cliente> findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
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
        
        // cria o cliente primeiro
        clienteRepository.persist(cliente);

        cliente.setEnderecos(new ArrayList<>());
        cliente.setTelefones(new ArrayList<>());

        // endereco e telefone associado a ele
        for (EnderecoRequestDTO enderecoDTO : dto.enderecos()) {
            Endereco endereco = enderecoServiceImpl.create(enderecoDTO, cliente.getId());
            cliente.getEnderecos().add(endereco);
        }

        for (TelefoneClienteRequestDTO telefoneDTO : dto.telefones()) {
            TelefoneCliente telefone = telefoneClienteServiceImpl.create(telefoneDTO, cliente.getId());
            cliente.getTelefones().add(telefone);
        }
        
        //Atualiza o cliente no banco
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Transactional
    public void addEndereco(Long clienteId, EnderecoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId);
        if (cliente != null) {
            Endereco endereco = new Endereco();
            endereco.setCep(dto.cep());
            endereco.setLogradouro(dto.logradouro());
            endereco.setNumero(dto.numero());

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

        // Atualiza os endereços do cliente
        // Limpa os endereços existentes
        cliente.getEnderecos().clear();

        cliente.setEnderecos(new ArrayList<>());

        // endereco associado a ele
        for (EnderecoRequestDTO enderecoDTO : dto.enderecos()) {
            Endereco endereco = enderecoServiceImpl.create(enderecoDTO, cliente.getId());
            cliente.getEnderecos().add(endereco);
        }

        // Persistindo as alterações do cliente
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