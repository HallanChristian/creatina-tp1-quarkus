package br.unitins.tp1.creatina.service.fornecedor;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.model.Municipio;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.repository.FornecedorRepository;
import br.unitins.tp1.creatina.repository.MunicipioRepository;
import br.unitins.tp1.creatina.repository.TelefoneRepository;
import br.unitins.tp1.creatina.service.telefone.TelefoneServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {

    @Inject
    public FornecedorRepository fornecedorRepository;

    @Inject
    public TelefoneServiceImpl telefoneServiceImpl;

    @Inject
    public TelefoneRepository telefoneRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Override
    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    @Override
    public List<Fornecedor> findByNome(String nome) {
        return fornecedorRepository.findByNome(nome);
    }

    @Override
    public Fornecedor findByCnpj(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }

    @Override
    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll().list();
    }

    @Override
    @Transactional
    public Fornecedor create(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
    
        fornecedor.setTelefones(new ArrayList<>());
        
        fornecedorRepository.persist(fornecedor);

        for (TelefoneRequestDTO telefoneDTO : dto.telefones()) {
            Telefone telefone = new Telefone();
            telefone.setDdd(telefoneDTO.ddd());
            telefone.setNumero(telefoneDTO.numero());

            telefoneRepository.persist(telefone);

            fornecedor.getTelefones().add(telefone);
        }

        return fornecedor;
    }
    
    @Override
    @Transactional
    public Fornecedor update(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new EntityNotFoundException("Fornecedor não encontrado");
        }
        
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        
        fornecedor.getTelefones().clear();
        
        fornecedorRepository.persist(fornecedor);
        return fornecedor;
    }
    
    @Override
    @Transactional
    public void addTelefone(Long fornecedorId, TelefoneRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId);
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor com ID " + fornecedorId + " não encontrado.");
        }
        
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
            
        fornecedor.getTelefones().add(telefone);
        
        telefoneRepository.persist(telefone);
        fornecedorRepository.persist(fornecedor);
    }
    
    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado.");
        }
        
        Telefone telefone = telefoneRepository.findById(idTelefone);
        if (telefone == null || !fornecedor.getTelefones().contains(telefone)) {
            throw new IllegalArgumentException("Telefone com ID " + idTelefone + " não encontrado para o fornecedor.");
        }
        
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
        telefoneRepository.persist(telefone); // Atualiza o telefone no banco de dados
    }

    
    @Override
    @Transactional
    public void addEndereco(Long fornecedorId, EnderecoRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId);
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor com ID " + fornecedorId + " não encontrado.");
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

        fornecedor.getEnderecos().add(endereco);
        fornecedorRepository.persist(fornecedor);
    }

    
    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado.");
        }
    
        Endereco endereco = fornecedor.getEnderecos().stream()
            .filter(e -> e.getId().equals(idEndereco))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado para o fornecedor."));

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
    
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setMunicipio(municipio);
        endereco.setCep(dto.cep());
    
        fornecedorRepository.persist(fornecedor);
    }
    

    @Override
    @Transactional
    public void delete(Long id) {
        fornecedorRepository.deleteById(id);
    }


}