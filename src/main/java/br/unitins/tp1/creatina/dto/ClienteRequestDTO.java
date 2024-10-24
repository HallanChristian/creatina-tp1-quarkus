package br.unitins.tp1.creatina.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO Cliente

public record ClienteRequestDTO(
    // Nome do cliente (obrigatório)
    @NotBlank(message = "Informe o nome do cliente.")
    String nome, 

    // CPF do cliente (obrigatório)
    @NotBlank(message = "Informe o CPF do cliente.")
    String cpf,

    // Data de nascimento (obrigatória)
    @NotNull(message = "Informe a data de nascimento do cliente.")
    LocalDate dataNascimento,

    // E-mail do cliente (obrigatório)
    @NotBlank(message = "Informe o e-mail do cliente.")
    String email,

    List<TelefoneClienteRequestDTO> telefones,
    List<EnderecoRequestDTO> enderecos
) {}
