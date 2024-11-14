package br.unitins.tp1.creatina.dto;

import br.unitins.tp1.creatina.model.PessoaFisica;
import br.unitins.tp1.creatina.model.Sexo;

public record PessoaFisicaResponseDTO(Long id, String nome, String cpf, Sexo sexo, String nomeImagem) {

    public static PessoaFisicaResponseDTO valueOf(PessoaFisica pessoafisica) {
        return new PessoaFisicaResponseDTO (
            pessoafisica.getId(), 
            pessoafisica.getNome(), 
            pessoafisica.getCpf(),
            pessoafisica.getSexo(),
            pessoafisica.getNomeImagem());
    }
    
}
