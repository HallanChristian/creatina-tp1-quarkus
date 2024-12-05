package br.unitins.tp1.creatina.service.cliente;

import java.util.List;

import br.unitins.tp1.creatina.dto.cliente.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.model.Usuario;
import br.unitins.tp1.creatina.repository.ClienteRepository;
import br.unitins.tp1.creatina.repository.TelefoneRepository;
import br.unitins.tp1.creatina.service.telefone.TelefoneServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public TelefoneRepository  telefoneRepository;

    @Inject
    public TelefoneServiceImpl  telefoneServiceImpl;

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    @Override
    public Cliente findByCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o CPF: " + cpf);
        }
        return cliente;
    }

    @Override
    public Cliente findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o Email: " + email);
        }
        return cliente;
    }

    @Override
    public Cliente findByUsername(String username) {
        Cliente cliente = clienteRepository.findByUsername(username);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o Username: " + username);
        }
        return cliente;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    @Override
    @Transactional
    public Cliente create(String username, ClienteRequestDTO dto) {
        // if (clienteRepository.existsByUsername(username)) {
        //     throw new IllegalArgumentException("Username já existe: " + username);
        // }

        Cliente cliente = new Cliente();
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setEmail(dto.email());
        clienteRepository.persist(cliente);

        return cliente;
    }

    @Override
    @Transactional
    public Cliente update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = findById(id);
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setEmail(dto.email());
        clienteRepository.persist(cliente);

        return cliente;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }

    @Override
    @Transactional
    public Endereco addEndereco(Long clienteId, EnderecoRequestDTO dto) {
        Cliente cliente = findById(clienteId);
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        cliente.getEnderecos().add(endereco);
        clienteRepository.persist(cliente);

        return endereco;
    }

    @Override
    @Transactional
    public void updateEndereco(String username, Long idEndereco, EnderecoRequestDTO dto) {
        Cliente cliente = findByUsername(username);
        Endereco endereco = cliente.getEnderecos().stream()
                .filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado com o ID: " + idEndereco));
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        clienteRepository.persist(cliente);
    }

    @Override
    @Transactional
    public Telefone addTelefone(Long clienteId, TelefoneRequestDTO dto) {
        Cliente cliente = findById(clienteId);
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        cliente.getTelefones().add(telefone);
        clienteRepository.persist(cliente);

        return telefone;
    }

    @Override
    @Transactional
    public void updateTelefone(String username, Long idTelefone, TelefoneRequestDTO dto) {
        Cliente cliente = findByUsername(username);
        Telefone telefone = cliente.getTelefones().stream()
                .filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Telefone não encontrado com o ID: " + idTelefone));
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        clienteRepository.persist(cliente);
    }

    @Override
    @Transactional
    public void adicionarListaDesejo(String username, Long idProduto) {
        Cliente cliente = findByUsername(username);
        Creatina produto = new Creatina();
        produto.setId(idProduto);
        cliente.getListaDesejos().add(produto);
        clienteRepository.persist(cliente);
    }

    @Override
    @Transactional
    public void removerListaDesejo(String username, Long idProduto) {
        Cliente cliente = findByUsername(username);
        cliente.getListaDesejos().removeIf(produto -> produto.getId().equals(idProduto));
        clienteRepository.persist(cliente);
    }

    @Override
    public List<Creatina> getListaDesejos(String username) {
        Cliente cliente = findByUsername(username);
        return cliente.getListaDesejos();
    }
}