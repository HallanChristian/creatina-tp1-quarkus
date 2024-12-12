package br.unitins.tp1.creatina.service.categoria;

import java.util.List;

import br.unitins.tp1.creatina.dto.categoria.CategoriaRequestDTO;
import br.unitins.tp1.creatina.model.Categoria;
import br.unitins.tp1.creatina.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    public CategoriaRepository categoriaRepository;

    @Override
    public Categoria findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null)
            throw new EntityNotFoundException("Categoria não encontrado!");

        return categoria;

    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return categoriaRepository.findByNome(nome);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.listAll();
    }

    @Override
    @Transactional
    public Categoria create(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        categoriaRepository.persist(categoria);
        return categoria;
    }

    @Override
    @Transactional
    public void update(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null)
            throw new EntityNotFoundException("Categoria não encontrado!");

        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null)
            throw new EntityNotFoundException("Categoria não encontrado!");
        categoriaRepository.delete(categoria);
    }

}