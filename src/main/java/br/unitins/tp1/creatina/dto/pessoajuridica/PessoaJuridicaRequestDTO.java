package br.unitins.tp1.creatina.dto.pessoajuridica;

import jakarta.validation.constraints.NotBlank;

// DTO PessoaJuridica
public record PessoaJuridicaRequestDTO(
    // Nome da pessoa (obrigatório)
    @NotBlank(message = "Informe o nome da pessoa.")
    String nome, 

    // CNPJ da pessoa (obrigatório)
    @NotBlank(message = "Informe o CNPJ da pessoa.")
    String cnpj,

    String email

) {}
