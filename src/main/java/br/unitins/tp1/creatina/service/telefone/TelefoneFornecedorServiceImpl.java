package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneFornecedor;
import br.unitins.tp1.creatina.repository.telefone.TelefoneFornecedorRepository;
import br.unitins.tp1.creatina.service.fornecedor.FornecedorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TelefoneFornecedorServiceImpl implements TelefoneFornecedorService {

    @Inject
    public TelefoneFornecedorRepository telefonefornecedorRepository;

    @Inject
    public FornecedorService fornecedorService;

    @Override
    public TelefoneFornecedor findById(Long id) {
        return telefonefornecedorRepository.findById(id);
    }

    @Override
    public List<TelefoneFornecedor> findByFornecedor(Long id) {
        return telefonefornecedorRepository.findByTelefoneFornecedor(id);
    }

    @Override
    public List<TelefoneFornecedor> findAll() {
        return telefonefornecedorRepository.findAll().list();
    }

    @Override
    @Transactional
    public TelefoneFornecedor create(TelefoneFornecedorRequestDTO dto) {
        TelefoneFornecedor telefone = new TelefoneFornecedor();

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        telefonefornecedorRepository.persist(telefone);

        return telefone;
    }

    @Override
    @Transactional
    public TelefoneFornecedor update(Long id, TelefoneFornecedorRequestDTO dto) {
        TelefoneFornecedor telefone = telefonefornecedorRepository.findById(id);

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        telefonefornecedorRepository.persist(telefone);

        return telefone;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        telefonefornecedorRepository.deleteById(id);
    }

}