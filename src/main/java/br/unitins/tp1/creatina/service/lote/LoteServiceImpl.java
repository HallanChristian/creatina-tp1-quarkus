package br.unitins.tp1.creatina.service.lote;

import java.util.List;

import br.unitins.tp1.creatina.dto.LoteRequestDTO;
import br.unitins.tp1.creatina.model.Lote;
import br.unitins.tp1.creatina.repository.lote.LoteRepository;
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
        // buscando o estado a partir de um id do lote
        Lote lote = new Lote();
        lote.setCreatina(creatinaService.findById(dto.idCreatina()));
        lote.setCodigo(dto.codigo());
        lote.setData(dto.data());
        lote.setEstoque(dto.estoque());

        //salvando o lote
        loteRepository.persist(lote);
        
        return lote;
    }

    @Override
    @Transactional
    public Lote update(Long id, LoteRequestDTO dto) {
        Lote lote = loteRepository.findById(id);

        lote.setCreatina(creatinaService.findById(dto.idCreatina()));
        lote.setCodigo(dto.codigo());
        lote.setData(dto.data());
        lote.setEstoque(dto.estoque());

        return lote;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        loteRepository.deleteById(id);
    }
    
}