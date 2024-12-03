package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Telefone;
import br.unitins.tp1.creatina.repository.TelefoneRepository;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    @Inject
    public TelefoneRepository telefoneRepository;

    @Inject
    public ClienteService clienteService;

    @Override
    public Telefone findById(Long id) {
        return telefoneRepository.findById(id);
    }

    @Override
    public List<Telefone> findByCliente(Long id) {
        return telefoneRepository.findByCliente(id);
    }

    @Override
    public List<Telefone> findByFuncionario(Long id) {
        return telefoneRepository.findByFuncionario(id);
    }

    @Override
    public List<Telefone> findByFornecedor(Long id) {
        return telefoneRepository.findByFornecedor(id);
    }

    @Override
    public List<Telefone> findAll() {
        return telefoneRepository.findAll().list();
    }
    
    
    @Override
    public List<Telefone> findByNumero(String numero) {
        return telefoneRepository.findByNumero(numero);
    }

    @Override
    public List<Telefone> findByDdd(String ddd) {
        return telefoneRepository.findByDdd(ddd);
    }
    
    @Override
    @Transactional
    public Telefone update(Long id, TelefoneRequestDTO dto) {
        Telefone telefone = telefoneRepository.findById(id);

        if (telefone == null) {
            throw new IllegalArgumentException("Telefone não encontrado com o id: " + id);
        }

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        telefoneRepository.persist(telefone);
        return telefone;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        telefoneRepository.deleteById(id);
    }

}