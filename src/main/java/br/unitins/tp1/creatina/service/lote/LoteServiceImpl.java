package br.unitins.tp1.creatina.service.lote;

import java.util.List;

import br.unitins.tp1.creatina.dto.lote.LoteRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.Lote;
import br.unitins.tp1.creatina.repository.EnderecoRepository;
import br.unitins.tp1.creatina.repository.LoteRepository;
import br.unitins.tp1.creatina.service.creatina.CreatinaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoteServiceImpl implements LoteService {

    @Inject
    public LoteRepository loteRepository;

    @Inject
    public CreatinaService creatinaService;

    @Inject
    public EnderecoRepository enderecoRepository;

    @Override
    public Lote findById(Long id) {
        return loteRepository.findById(id);
    }

    @Override
    public Lote findByIdCreatina(Long idCreatina) {
        return loteRepository.findByIdCreatina(idCreatina);
    }

    @Override
    public Lote findByCodigo(String codigo) {
        return loteRepository.findByCodigo(codigo);
    }

    @Override
    public List<Lote> findAll() {
        return loteRepository.findAll().list();
    }

    @Override
    @Transactional
    public Lote create(LoteRequestDTO dto) {
        Creatina creatina = creatinaService.findById(dto.idCreatina());
        if (creatina == null) {
            throw new IllegalArgumentException("Creatina não encontrada para o ID fornecido.");
        }

        Endereco localDistribuicao = enderecoRepository.findById(dto.idEndereco());
        if (localDistribuicao == null) {
            throw new IllegalArgumentException("Endereço não encontrado para o ID fornecido.");
        }

        Lote lote = new Lote();
        lote.setCreatina(creatina);
        lote.setCodigo(dto.codigo());
        lote.setDataFabricacao(dto.dataFabricacao());
        lote.setDataValidade(dto.dataValidade());
        lote.setEstoque(dto.estoque());
        lote.setLocalDistribuicao(localDistribuicao);

        loteRepository.persist(lote);

        return lote;
    }

    @Override
    @Transactional
    public Lote update(Long id, LoteRequestDTO dto) {
        Lote lote = loteRepository.findById(id);
        if (lote == null) {
            throw new IllegalArgumentException("Lote não encontrado para o ID fornecido.");
        }

        Creatina creatina = creatinaService.findById(dto.idCreatina());
        if (creatina == null) {
            throw new IllegalArgumentException("Creatina não encontrada para o ID fornecido.");
        }

        Endereco localDistribuicao = enderecoRepository.findById(dto.idEndereco());
        if (localDistribuicao == null) {
            throw new IllegalArgumentException("Endereço não encontrado para o ID fornecido.");
        }

        lote.setCreatina(creatina);
        lote.setCodigo(dto.codigo());
        lote.setDataFabricacao(dto.dataFabricacao());
        lote.setDataValidade(dto.dataValidade());
        lote.setEstoque(dto.estoque());
        lote.setLocalDistribuicao(localDistribuicao);

        return lote;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        loteRepository.deleteById(id);
    }
    
}