package br.unitins.tp1.creatina.service.endereco;

import java.util.List;

import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.repository.endereco.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    public EnderecoRepository enderecoRepository;

    @Override
    public Endereco findById(Long id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public List<Endereco> findByCliente(Long idCliente) {
        return enderecoRepository.findByCliente(idCliente);
    }

    @Override
    public List<Endereco> findAll() {
        return enderecoRepository.findAll().list();
    }

    @Override
    @Transactional
    public Endereco create(EnderecoRequestDTO dto) {
        Endereco endereco = new Endereco();

        endereco.setCep(dto.cep());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());

        enderecoRepository.persist(endereco);

        return endereco;
    }

    @Override
    @Transactional
    public Endereco update(Long id, EnderecoRequestDTO dto) {
        Endereco endereco = enderecoRepository.findById(id);

        endereco.setCep(dto.cep());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());

        return endereco;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByCliente(Long idCliente) {
        enderecoRepository.deleteByCliente(idCliente);
    }

}