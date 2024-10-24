package br.unitins.tp1.creatina.dto;

import jakarta.validation.constraints.NotBlank;

// DTO PessoaFisica
public record PessoaFisicaRequestDTO(
    // Nome da pessoa (obrigatório)
    @NotBlank(message = "Informe o nome da pessoa.")
    String nome, 

    // CPF da pessoa (obrigatório)
    @NotBlank(message = "Informe o CPF da pessoa.")
    String cpf,

    // ID do sexo (opcional)
    Integer idSexo
) {}
