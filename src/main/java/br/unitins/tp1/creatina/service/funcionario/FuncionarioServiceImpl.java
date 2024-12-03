package br.unitins.tp1.creatina.service.funcionario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.Funcionario;
import br.unitins.tp1.creatina.model.Municipio;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.repository.FuncionarioRepository;
import br.unitins.tp1.creatina.repository.MunicipioRepository;
import br.unitins.tp1.creatina.repository.TelefoneRepository;
import br.unitins.tp1.creatina.service.telefone.TelefoneServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    public FuncionarioRepository funcionarioRepository;

    @Inject
    public TelefoneServiceImpl telefoneServiceImpl;

    @Inject
    public TelefoneRepository telefoneRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome);
    }

    @Override
    public Funcionario findByUsername(String username) {
        return funcionarioRepository.findByUsername(username);
    }
    
        @Override
        public Funcionario findByCpf(String cpf) {
            return funcionarioRepository.findByCpf(cpf);
        }
    
        @Override
        public Funcionario findByEmail(String email) {
            return funcionarioRepository.findByEmail(email);
        }

    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll().list();
    }

    @Override
    @Transactional
    public Funcionario create(FuncionarioRequestDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.nome());
        funcionario.setCargo(dto.cargo());
        funcionario.setCpf(dto.cpf());
        funcionario.setDataContratacao(dto.dataContratacao());
        funcionario.setSalario(dto.salario());
        funcionario.setDataNascimento(dto.dataNascimento());
        funcionario.setEmail(dto.email());
    
        funcionario.setTelefones(new ArrayList<>());
        
        funcionarioRepository.persist(funcionario);

        for (TelefoneRequestDTO telefoneDTO : dto.telefones()) {
            Telefone telefone = new Telefone();
            telefone.setDdd(telefoneDTO.ddd());
            telefone.setNumero(telefoneDTO.numero());

            telefoneRepository.persist(telefone);

            funcionario.getTelefones().add(telefone);
        }

        return funcionario;
    }
    
    @Override
    @Transactional
    public Funcionario update(Long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new EntityNotFoundException("Funcionario não encontrado");
        }
        
        funcionario.setNome(dto.nome());
        funcionario.setCargo(dto.cargo());
        funcionario.setCpf(dto.cpf());
        funcionario.setDataContratacao(dto.dataContratacao());
        funcionario.setSalario(dto.salario());
        funcionario.setDataNascimento(dto.dataNascimento());
        funcionario.setEmail(dto.email());
        
        funcionario.getTelefones().clear();
        
        funcionarioRepository.persist(funcionario);
        return funcionario;
    }
    
    @Override
    @Transactional
    public void addTelefone(Long funcionarioId, TelefoneRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com ID " + funcionarioId + " não encontrado.");
        }
        
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
            
        funcionario.getTelefones().add(telefone);
        
        telefoneRepository.persist(telefone);
        funcionarioRepository.persist(funcionario);
    }
    
    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com ID " + id + " não encontrado.");
        }
        
        Telefone telefone = telefoneRepository.findById(idTelefone);
        if (telefone == null || !funcionario.getTelefones().contains(telefone)) {
            throw new IllegalArgumentException("Telefone com ID " + idTelefone + " não encontrado para o funcionario.");
        }
        
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        telefoneRepository.persist(telefone); // Atualiza o telefone no banco de dados
    }
    
    @Override
    @Transactional
    public void addEndereco(Long funcionarioId, EnderecoRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com ID " + funcionarioId + " não encontrado.");
        }

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        if (municipio == null) {
            throw new IllegalArgumentException("Município com ID " + dto.idMunicipio() + " não encontrado.");
        }

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setMunicipio(municipio);
        endereco.setCep(dto.cep());

        funcionario.getEnderecos().add(endereco);
        funcionarioRepository.persist(funcionario);
    }

    
    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com ID " + id + " não encontrado.");
        }
    
        Endereco endereco = funcionario.getEnderecos().stream()
            .filter(e -> e.getId().equals(idEndereco))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado para o funcionario."));

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
    
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setMunicipio(municipio);
        endereco.setCep(dto.cep());
    
        funcionarioRepository.persist(funcionario);
    }
    

    @Override
    @Transactional
    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }


}