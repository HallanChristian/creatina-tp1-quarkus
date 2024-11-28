package br.unitins.tp1.creatina.service.fornecedor;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.model.TelefoneFornecedor;
import br.unitins.tp1.creatina.repository.fornecedor.FornecedorRepository;
import br.unitins.tp1.creatina.repository.telefone.TelefoneFornecedorRepository;
import br.unitins.tp1.creatina.service.telefone.TelefoneFornecedorServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {

    @Inject
    public FornecedorRepository fornecedorRepository;

    @Inject
    public TelefoneFornecedorServiceImpl telefoneFornecedorServiceImpl;

    @Inject
    public TelefoneFornecedorRepository telefoneFornecedorRepository;

    @Override
    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    @Override
    public List<Fornecedor> findByNome(String nome) {
        return fornecedorRepository.findByNome(nome);
    }

    @Override
    public List<Fornecedor> findByCnpj(String cnpj) {
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
  
        fornecedorRepository.persist(fornecedor);

        fornecedor.setTelefones(new ArrayList<>());

        for (TelefoneFornecedorRequestDTO telefoneDTO : dto.telefones()) {
            TelefoneFornecedor telefone = telefoneFornecedorServiceImpl.create(telefoneDTO);
            fornecedor.getTelefones().add(telefone);
        }
        
        fornecedorRepository.persist(fornecedor);

        return fornecedor;
    }

    @Override
    @Transactional
    public void addTelefone(Long fornecedorId, TelefoneFornecedorRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId);
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor com ID " + fornecedorId + " não encontrado.");
        }
        TelefoneFornecedor telefone = telefoneFornecedorServiceImpl.create(dto);
        telefone.setFornecedor(fornecedor);
        telefoneFornecedorRepository.persist(telefone);
        fornecedor.getTelefones().add(telefone);
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

        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());

        fornecedor.getTelefones().clear();

        fornecedorRepository.persist(fornecedor);
        return fornecedor;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fornecedorRepository.deleteById(id);
    }

}