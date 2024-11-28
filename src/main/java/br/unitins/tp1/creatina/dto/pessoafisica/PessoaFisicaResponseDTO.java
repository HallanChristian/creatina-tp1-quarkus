package br.unitins.tp1.creatina.dto.pessoafisica;

import java.time.LocalDate;

import br.unitins.tp1.creatina.model.PessoaFisica;

public record PessoaFisicaResponseDTO(
    Long id, 
    String nome,
    String email,
    LocalDate dataNascimento,
    String cpf 
    ) {

        public static PessoaFisicaResponseDTO valueOf(PessoaFisica pessoaFisica) {
            if (pessoaFisica == null) {
                throw new IllegalArgumentException("PessoaFisica não pode ser nula.");
            }

            return new PessoaFisicaResponseDTO (
                pessoaFisica.getId(), 
                pessoaFisica.getNome(),
                pessoaFisica.getEmail(),
                pessoaFisica.getDataNascimento(), 
                pessoaFisica.getCpf());
    }
    
}
