package br.unitins.tp1.faixas.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.faixas.dto.ClienteRequestDTO;
import br.unitins.tp1.faixas.dto.EnderecoRequestDTO;
import br.unitins.tp1.faixas.dto.TelefoneRequestDTO;
import br.unitins.tp1.faixas.model.Cliente;
import br.unitins.tp1.faixas.model.Endereco;
import br.unitins.tp1.faixas.model.Telefone;
import br.unitins.tp1.faixas.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public MunicipioService municipioService;

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
        cliente.setTelefones(getTelefones(dto));
        cliente.setEnderecos(getEnderecos(dto));

        clienteRepository.persist(cliente);

        return cliente;
    }

    @Override
    @Transactional
    public Cliente update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setEmail(dto.email());
        updateTelefones(cliente, dto.telefones());
        updateEnderecos(cliente, dto.enderecos());

        return cliente;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    private List<Telefone> getTelefones(ClienteRequestDTO dto) {
        List<Telefone> telefones = new ArrayList<>();

        for (int i = 0; i < dto.telefones().size(); i++) {
            Telefone telefone = new Telefone();
            TelefoneRequestDTO telefoneRequestDTO = dto.telefones().get(i);
            telefone.setDdd(telefoneRequestDTO.ddd());
            telefone.setNumero(telefoneRequestDTO.numero());
            telefones.add(telefone);
        }

        return telefones;
    }

    private List<Endereco> getEnderecos(ClienteRequestDTO dto) {
        List<Endereco> enderecos = new ArrayList<>();

        for (int i = 0; i < dto.enderecos().size(); i++) {
            Endereco endereco = new Endereco();
            EnderecoRequestDTO enderecoRequestDTO = dto.enderecos().get(i);
            endereco.setLogradouro(enderecoRequestDTO.logradouro());
            endereco.setNumero(enderecoRequestDTO.numero());
            endereco.setCep(enderecoRequestDTO.cep());
            endereco.setMunicipio(municipioService.findById(enderecoRequestDTO.idMunicipio()));
            enderecos.add(endereco);
        }

        return enderecos;
    }

    private void updateTelefones(Cliente cliente, List<TelefoneRequestDTO> novosTelefonesDTO) {
        List<Telefone> telefonesExistentes = cliente.getTelefones();
    
        telefonesExistentes.removeIf(telefone -> 
            novosTelefonesDTO.stream().noneMatch(dto -> 
                dto.ddd().equals(telefone.getDdd()) && dto.numero().equals(telefone.getNumero()))
        );
    
        // Adicionar ou atualizar telefones
        for (TelefoneRequestDTO dto : novosTelefonesDTO) {
            Telefone telefoneExistente = telefonesExistentes.stream()
                .filter(t -> t.getDdd().equals(dto.ddd()) && t.getNumero().equals(dto.numero()))
                .findFirst()
                .orElse(null);
    
            if (telefoneExistente == null) {
                Telefone novoTelefone = new Telefone();
                novoTelefone.setDdd(dto.ddd());
                novoTelefone.setNumero(dto.numero());
                telefonesExistentes.add(novoTelefone);
            } else {
                telefoneExistente.setDdd(dto.ddd());
                telefoneExistente.setNumero(dto.numero());
            }
        }
    }
    
    private void updateEnderecos(Cliente cliente, List<EnderecoRequestDTO> novosEnderecosDTO) {
        List<Endereco> enderecosExistentes = cliente.getEnderecos();
    
        enderecosExistentes.removeIf(endereco -> 
            novosEnderecosDTO.stream().noneMatch(dto -> 
                dto.logradouro().equals(endereco.getLogradouro()) && dto.numero().equals(endereco.getNumero()))
        );
    
        for (EnderecoRequestDTO dto : novosEnderecosDTO) {
            Endereco enderecoExistente = enderecosExistentes.stream()
                .filter(e -> e.getLogradouro().equals(dto.logradouro()) && e.getNumero().equals(dto.numero()))
                .findFirst()
                .orElse(null);
    
            if (enderecoExistente == null) {
                Endereco novoEndereco = new Endereco();
                novoEndereco.setLogradouro(dto.logradouro());
                novoEndereco.setNumero(dto.numero());
                novoEndereco.setCep(dto.cep());
                novoEndereco.setMunicipio(municipioService.findById(dto.idMunicipio()));
                enderecosExistentes.add(novoEndereco);
            } else {
                enderecoExistente.setLogradouro(dto.logradouro());
                enderecoExistente.setNumero(dto.numero());
                enderecoExistente.setCep(dto.cep());
                enderecoExistente.setMunicipio(municipioService.findById(dto.idMunicipio()));
            }
        }
    
}
}
