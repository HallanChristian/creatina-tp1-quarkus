package br.unitins.tp1.creatina.dto.pessoajuridica;

import br.unitins.tp1.creatina.model.PessoaJuridica;

public record PessoaJuridicaResponseDTO(
    Long id, 
    String nome,
    String cnpj 
    ) {

        public static PessoaJuridicaResponseDTO valueOf(PessoaJuridica pessoaJuridica) {
            if (pessoaJuridica == null) {
                throw new IllegalArgumentException("PessoaJuridica não pode ser nula.");
            }

            return new PessoaJuridicaResponseDTO (
                pessoaJuridica.getId(), 
                pessoaJuridica.getNome(),
                pessoaJuridica.getCnpj());
    }
    
}
