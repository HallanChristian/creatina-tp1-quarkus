package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.PessoaFisica;
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
    @Transactional
    public Telefone create(Long idCliente, TelefoneRequestDTO dto) {
        Telefone telefone = TelefoneRequestDTO.convert(dto);
        PessoaFisica cliente = clienteService.findById(idCliente); 

        List<Telefone> telefones = cliente.getTelefones(); 
        telefones.add(telefone);

        telefoneRepository.persist(telefone);

        return telefone;
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

    @Override
    @Transactional
    public void deleteTelefoneByCliente(Long idCliente, Long idTelefone) {
        telefoneRepository.deleteTelefoneByCliente(idCliente, idTelefone);
    }

    @Override
    @Transactional
    public void deleteTelefoneByFuncionario(Long idFuncionario, Long idTelefone) {
        telefoneRepository.deleteTelefoneByFuncionario(idFuncionario, idTelefone);
    }

    @Override
    @Transactional
    public void deleteTelefoneByFornecedor(Long idFornecedor, Long idTelefone) {
        telefoneRepository.deleteTelefoneByFornecedor(idFornecedor, idTelefone);
    }

    @Override
    @Transactional
    public void deleteAllByClienteId(Long idCliente) {
        telefoneRepository.deleteByCliente(idCliente);
    }

    @Override
    @Transactional
    public void deleteAllByFuncionarioId(Long idFuncionario) {
        telefoneRepository.deleteByFuncionario(idFuncionario);
    }

    @Override
    @Transactional
    public void deleteAllByFornecedorId(Long idFornecedor) {
        telefoneRepository.deleteByFornecedor(idFornecedor);
    }

}