package br.unitins.tp1.creatina.service.creatina;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.creatina.dto.creatina.CreatinaRequestDTO;
import br.unitins.tp1.creatina.model.Creatina;


public interface CreatinaService {

    Creatina findById(Long id);

    List<Creatina> findByNome(String nome);

    List<Creatina> findByMarca(String marca);

    List<Creatina> findByTipo(String tipo);

    List<Creatina> findByPreco(BigDecimal precoMin, BigDecimal precoMax);

    List<Creatina> findByFilters(String nome, String marca, String tipo, BigDecimal precoMin, BigDecimal precoMax);

    List<Creatina> findAll();

    Creatina create(CreatinaRequestDTO dto);

    void update(Long id, CreatinaRequestDTO dto);

    void delete(Long id);

    Creatina updateNomeImagem(Long id, String nomeImagem); 
    
}
