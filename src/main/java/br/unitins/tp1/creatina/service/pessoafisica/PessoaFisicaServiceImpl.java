package br.unitins.tp1.creatina.service.pessoafisica;

import java.util.List;

import br.unitins.tp1.creatina.dto.PessoaFisicaRequestDTO;
import br.unitins.tp1.creatina.model.PessoaFisica;
import br.unitins.tp1.creatina.model.Sexo;
import br.unitins.tp1.creatina.repository.pessoafisica.PessoaFisicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

    @Inject
    public PessoaFisicaRepository pessoafisicaRepository;

    @Override
    public PessoaFisica findById(Long id) {
        return pessoafisicaRepository.findById(id);
    }

    @Override
    public List<PessoaFisica> findByNome(String nome) {
        return pessoafisicaRepository.findByNome(nome);
    }

    @Override
    public List<PessoaFisica> findAll() {
        return pessoafisicaRepository.findAll().list();
    }

    @Override
    @Transactional
    public PessoaFisica create(PessoaFisicaRequestDTO dto) {
        PessoaFisica pessoafisica = new PessoaFisica();
        pessoafisica.setNome(dto.nome());
        pessoafisica.setCpf(dto.cpf());
        pessoafisica.setSexo(Sexo.valueOf(dto.idSexo()));

        pessoafisicaRepository.persist(pessoafisica);
        return pessoafisica;
    }

    @Override
    @Transactional
    public PessoaFisica update(Long id, PessoaFisicaRequestDTO dto) {
        PessoaFisica pessoafisica = pessoafisicaRepository.findById(id);

        pessoafisica.setNome(dto.nome());
        pessoafisica.setCpf(dto.cpf());
        pessoafisica.setSexo(Sexo.valueOf(dto.idSexo()));

        return pessoafisica;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pessoafisicaRepository.deleteById(id);
    }
    
}
