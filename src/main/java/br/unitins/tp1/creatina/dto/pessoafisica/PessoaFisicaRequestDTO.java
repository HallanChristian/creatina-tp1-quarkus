package br.unitins.tp1.creatina.dto.pessoafisica;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO PessoaFisica
public record PessoaFisicaRequestDTO(
    // Nome da pessoa (obrigatório)
    @NotBlank(message = "Informe o nome da pessoa.")
    String nome, 

    // CPF da pessoa (obrigatório)
    @NotBlank(message = "Informe o CPF da pessoa.")
    String cpf,

    @NotNull(message = "Informe a data de nascimento da pessoa.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dataNascimento

) {}
