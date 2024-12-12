package br.unitins.tp1.creatina.service.cliente;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.cliente.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.PessoaFisica;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.model.Usuario;
import br.unitins.tp1.creatina.repository.ClienteRepository;
import br.unitins.tp1.creatina.repository.PessoaFisicaRepository;
import br.unitins.tp1.creatina.repository.TelefoneRepository;
import br.unitins.tp1.creatina.repository.UsuarioRepository;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
import br.unitins.tp1.creatina.service.endereco.EnderecoService;
import br.unitins.tp1.creatina.service.hash.HashService;
import br.unitins.tp1.creatina.service.telefone.TelefoneService;
import br.unitins.tp1.creatina.service.telefone.TelefoneServiceImpl;
import br.unitins.tp1.creatina.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
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

    @Inject
    public EnderecoService enderecoService;

    @Inject
    public TelefoneService telefoneService;

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    public CreatinaService creatinaService;

    @Inject
    public HashService hashService;

    @Override
    public Cliente findById(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("id", "O ID deve ser um valor positivo e válido.");
        }
        return clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        List<Cliente> clientes = clienteRepository.findByNome(nome);
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("nome", "O nome não pode estar vazio.");
        }
        if (clientes == null || clientes.isEmpty()) {
            throw new NotFoundException("Nenhum cliente encontrado com o nome: " + nome);
        }
        return clientes;
    }

    @Override
    public Cliente findByCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new ValidationException("cpf", "O cpf não pode estar vazio.");
        }
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o CPF: " + cpf);
        }
        return cliente;
    }

    @Override
    public Cliente findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("email", "O email não pode estar vazio.");
        }
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o Email: " + email);
        }
        return cliente;
    }

    @Override
    public Cliente findByUsername(String username) {
        Cliente cliente = clienteRepository.findByUsername(username);
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("username", "O username não pode estar vazio.");
        }
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
        // Valida se o username já existe
        if (existeUsername(username)) {
            throw new ValidationException("username", "O username informado já está em uso.");
        }

        if (dto == null) {
            throw new ValidationException("clienteRequestDTO", "O objeto DTO não pode ser nulo.");
        }

        Usuario usuario = usuarioRepository.findByUsername(username);
        
        Cliente cliente = new Cliente();
        PessoaFisica pf = new PessoaFisica();
        cliente.setUsuario(usuario);
        pf.setNome(dto.nome());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());
        clienteRepository.persist(cliente);
        pf.setEnderecos(new ArrayList<>());
        pf.setTelefones(new ArrayList<>());

        for (EnderecoRequestDTO enderecoDTO : dto.enderecos()) {
            Endereco endereco = enderecoService.create(enderecoDTO);
            pf.getEnderecos().add(endereco);
        }

        for (TelefoneRequestDTO telefoneDTO : dto.telefones()) {
            Telefone telefone = telefoneService.create(telefoneDTO);
            pf.getTelefones().add(telefone);
        }
        pessoaFisicaRepository.persist(pf);
        cliente.setPessoaFisica(pf);
        clienteRepository.persist(cliente);

        return cliente;
    }

    // Método para verificar se o username já existe
    private boolean existeUsername(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }

    @Override
    @Transactional
    public Cliente update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = findById(id);
        PessoaFisica pf = new PessoaFisica();
        pf.setNome(dto.nome());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());
        clienteRepository.persist(cliente);

        return cliente;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (id == null || id <= 0) {
            throw new ValidationException("id", "O ID deve ser um valor positivo e válido.");
        }
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }
        clienteRepository.delete(cliente);
    }

    @Override
    @Transactional
    public Endereco addEndereco(String username, EnderecoRequestDTO dto) {
        Cliente cliente = clienteRepository.findByUsername(username);
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        cliente.getPessoaFisica().getEnderecos().add(endereco);
        clienteRepository.persist(cliente);

        return endereco;
    }

    @Override
    @Transactional
    public void updateEndereco(String username, Long idEndereco, EnderecoRequestDTO dto) {
        Cliente cliente = findByUsername(username);
        PessoaFisica pf = new PessoaFisica();
        Endereco endereco = pf.getEnderecos().stream()
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
    public Telefone addTelefone(String username, TelefoneRequestDTO dto) {
        Cliente cliente = clienteRepository.findByUsername(username);
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        cliente.getPessoaFisica().getTelefones().add(telefone);
        clienteRepository.persist(cliente);

        return telefone;
    }

    @Override
    @Transactional
    public void updateTelefone(String username, Long idTelefone, TelefoneRequestDTO dto) {
        Cliente cliente = findByUsername(username);
        PessoaFisica pf = new PessoaFisica();
        Telefone telefone = pf.getTelefones().stream()
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
        Cliente cliente = clienteRepository.findByUsername(username);

        if (cliente.getListaDesejos() == null) {
            cliente.setListaDesejos(new ArrayList<>());
            return;
        }

        Creatina creatina = creatinaService.findById(idProduto);
        if (creatina == null) {
            throw new ValidationException("idProduto", "Creatina não encontrada.");
        }
        cliente.getListaDesejos().add(creatina);
    }

    @Override
    @Transactional
    public void removerListaDesejo(String username, Long idProduto) {
        Cliente cliente = findByUsername(username);
        List<Creatina> listaDesejos = cliente.getListaDesejos();
        if (listaDesejos == null || listaDesejos.isEmpty()) {
            throw new ValidationException("listaDesejos", "Você não possui uma lista de desejos.");
        }

        Creatina creatina = creatinaService.findById(idProduto);
        if (creatina == null || !listaDesejos.contains(creatina)) {
            throw new ValidationException("idProduto", "O produto não está na lista de desejos.");
        }
        listaDesejos.remove(creatina);
    }

    @Override
    public List<Creatina> getListaDesejos(String username) {
        Cliente cliente = findByUsername(username);
        return cliente.getListaDesejos();
    }
}