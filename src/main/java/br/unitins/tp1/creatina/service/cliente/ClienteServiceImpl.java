package br.unitins.tp1.creatina.service.cliente;

import java.util.List;
import java.util.stream.Collectors;

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
        populateCliente(cliente, dto);

        // Adiciona endereços ao cliente
        List<Endereco> enderecos = dto.enderecos().stream()
            .map(enderecoDTO -> {
                Endereco endereco = enderecoServiceImpl.create(enderecoDTO);
                endereco.setCliente(cliente);  // Associa o endereço ao cliente
                return endereco;
            }).collect(Collectors.toList());
        cliente.setEnderecos(enderecos);

        // Adiciona telefones ao cliente
        List<TelefoneCliente> telefones = dto.telefones().stream()
            .map(telefoneDTO -> {
                TelefoneCliente telefone = telefoneClienteServiceImpl.create(telefoneDTO);
                telefone.setCliente(cliente);  // Associa o telefone ao cliente
                return telefone;
            }).collect(Collectors.toList());
        cliente.setTelefones(telefones);

        clienteRepository.persist(cliente); // Persiste o cliente com endereços e telefones

        return cliente;
    }

   private void populateCliente(Cliente cliente, ClienteRequestDTO dto) {
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setEmail(dto.email());
    }

    @Override
    @Transactional
    public Cliente update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }

        populateCliente(cliente, dto);

        // Atualiza endereços
        cliente.getEnderecos().clear(); // Limpa os endereços existentes
        for (EnderecoRequestDTO enderecoDTO : dto.enderecos()) {
            Endereco endereco = enderecoServiceImpl.create(enderecoDTO);
            endereco.setCliente(cliente);
            cliente.getEnderecos().add(endereco); // Adiciona novo endereço
        }

        // Atualiza telefones
        cliente.getTelefones().clear(); // Limpa os telefones existentes
        for (TelefoneClienteRequestDTO telefoneDTO : dto.telefones()) {
            TelefoneCliente telefone = telefoneClienteServiceImpl.create(telefoneDTO);
            telefone.setCliente(cliente);
            cliente.getTelefones().add(telefone); // Adiciona novo telefone
        }

        clienteRepository.persist(cliente); // Persistir as alterações
        return cliente;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addEndereco(Long clienteId, EnderecoRequestDTO dto) {
        Cliente cliente = findClienteOrThrow(clienteId);
        Endereco endereco = enderecoServiceImpl.create(dto);
        endereco.setCliente(cliente);
        enderecoRepository.persist(endereco);
        cliente.getEnderecos().add(endereco);
    }

    @Override
    @Transactional
    public void addTelefone(Long clienteId, TelefoneClienteRequestDTO dto) {
        Cliente cliente = findClienteOrThrow(clienteId);
        TelefoneCliente telefone = telefoneClienteServiceImpl.create(dto);
        telefone.setCliente(cliente);
        telefoneClienteRepository.persist(telefone);
        cliente.getTelefones().add(telefone);
    }

    private Cliente findClienteOrThrow(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente_ID " + clienteId + " não encontrado.");
        }
        return cliente;
    }
}