package br.unitins.tp1.creatina.service.pessoafisica;

import java.util.List;

import br.unitins.tp1.creatina.dto.PessoaFisicaRequestDTO;
import br.unitins.tp1.creatina.model.PessoaFisica;


public interface PessoaFisicaService {

    PessoaFisica findById(Long id);

    List<PessoaFisica> findByNome(String nome);

    List<PessoaFisica> findAll();

    PessoaFisica create(PessoaFisicaRequestDTO dto);

    PessoaFisica update(Long id, PessoaFisicaRequestDTO dto);

    void delete(Long id); 
    
}
