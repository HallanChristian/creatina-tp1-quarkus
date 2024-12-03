package br.unitins.tp1.creatina.service.creatina;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.creatina.dto.creatina.CreatinaRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.repository.CreatinaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreatinaServiceImpl implements CreatinaService {

    @Inject
    public CreatinaRepository creatinaRepository;

    @Override
    public Creatina findById(Long id) {
        return creatinaRepository.findById(id);
    }

    @Override
    public List<Creatina> findByNome(String nome) {
        return creatinaRepository.findByNome(nome);
    }

    @Override
    public List<Creatina> findByMarca(String marca) {
        return creatinaRepository.findByMarca(marca);
    }

    @Override
    public List<Creatina> findByTipo(String tipo) {
        return creatinaRepository.findByTipo(tipo);
    }

    @Override
    public List<Creatina> findByPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return creatinaRepository.findByPreco(precoMin, precoMax);
    }

    @Override
    public List<Creatina> findByFilters(String nome, String marca, String tipo, BigDecimal precoMin,BigDecimal precoMax) {
        return creatinaRepository.findByFilters(nome, marca, tipo, precoMin, precoMax);
    }

    @Override
    public List<Creatina> findAll() {
        return creatinaRepository.findAll().list();
    }

    @Override
    @Transactional
    public Creatina create(CreatinaRequestDTO dto) {
        Creatina creatina = new Creatina();

        creatina.setNome(dto.nome());
        creatina.setMarca(dto.marca());
        creatina.setQuantidadeEmGramas(dto.quantidadeEmGramas());
        creatina.setTipo(dto.tipo());
        creatina.setPreco(dto.preco());

        creatinaRepository.persist(creatina);
        return creatina;
    }

    @Override
    @Transactional
    public Creatina update(Long id, CreatinaRequestDTO dto) {
        Creatina creatina = creatinaRepository.findById(id);
        if (creatina == null) {
            throw new EntityNotFoundException("Creatina não encontrada");
        }

        creatina.setNome(dto.nome());
        creatina.setMarca(dto.marca());
        creatina.setQuantidadeEmGramas(dto.quantidadeEmGramas());
        creatina.setTipo(dto.tipo());
        creatina.setPreco(dto.preco());

        return creatina;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        creatinaRepository.deleteById(id);
    }

    
}
