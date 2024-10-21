package br.unitins.tp1.creatina.service.fornecedor;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.FornecedorRequestDTO;
import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.Fornecedor;
import br.unitins.tp1.creatina.model.TelefoneFornecedor;
import br.unitins.tp1.creatina.repository.fornecedor.FornecedorRepository;
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
    public TelefoneFornecedorServiceImpl telefoneServiceImpl;

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
        // cria o fornecedor primeiro
        fornecedorRepository.persist(fornecedor);

        fornecedor.setTelefones(new ArrayList<>());

        // telefone associado a ele
        for (TelefoneFornecedorRequestDTO telefoneDTO : dto.telefones()) {
            TelefoneFornecedor telefone = telefoneServiceImpl.create(telefoneDTO, fornecedor.getId());
            fornecedor.getTelefones().add(telefone);
        }
        
        
        //Atualiza o fornecedor no banco
        fornecedorRepository.persist(fornecedor);

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

        // Atualiza os dados do fornecedor
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        // Remove os antigos
        fornecedor.getTelefones().clear();

        // Persistindo as alterações do fornecedor
        fornecedorRepository.persist(fornecedor);
        return fornecedor;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fornecedorRepository.deleteById(id);
    }

}