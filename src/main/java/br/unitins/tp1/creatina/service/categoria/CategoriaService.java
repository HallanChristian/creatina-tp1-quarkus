package br.unitins.tp1.creatina.service.categoria;

import java.util.List;

import br.unitins.tp1.creatina.dto.categoria.CategoriaRequestDTO;
import br.unitins.tp1.creatina.model.Categoria;

public interface CategoriaService {

    Categoria findById(Long id);

    List<Categoria> findByNome(String nome);

    List<Categoria> findAll();

    Categoria create(CategoriaRequestDTO dto);

    void update(Long id, CategoriaRequestDTO dto);

    void delete(Long id);

}