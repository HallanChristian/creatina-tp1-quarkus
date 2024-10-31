package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.TelefoneClienteRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.TelefoneCliente;
import br.unitins.tp1.creatina.repository.telefone.TelefoneClienteRepository;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TelefoneClienteServiceImpl implements TelefoneClienteService {

    @Inject
    public TelefoneClienteRepository telefoneclienteRepository;

    @Inject
    public ClienteService clienteService;

    @Override
    public TelefoneCliente findById(Long id) {
        return telefoneclienteRepository.findById(id);
    }

    @Override
    public List<TelefoneCliente> findByCliente(Long id) {
        return telefoneclienteRepository.findByTelefoneCliente(id);
    }

    @Override
    public List<TelefoneCliente> findAll() {
        return telefoneclienteRepository.findAll().list();
    }

    @Override
    @Transactional
    public TelefoneCliente create(Long idCliente, TelefoneClienteRequestDTO dto) {
        TelefoneCliente telefone = new TelefoneCliente();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        Cliente cliente = clienteService.findById(idCliente);
        telefone.setCliente(cliente);

        telefoneclienteRepository.persist(telefone);
        return telefone;
    }

    @Override
    @Transactional
    public TelefoneCliente update(Long id, TelefoneClienteRequestDTO dto) {
        TelefoneCliente telefone = telefoneclienteRepository.findById(id);

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        telefoneclienteRepository.persist(telefone);

        return telefone;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        telefoneclienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByClienteId(Long idCliente) {
        List<TelefoneCliente> telefones = telefoneclienteRepository.findByTelefoneCliente(idCliente);
        for (TelefoneCliente telefone : telefones) {
            telefoneclienteRepository.delete(telefone);
        }
    }

}