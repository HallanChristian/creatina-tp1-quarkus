package br.unitins.tp1.faixas.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome, 
    @NotBlank(message = "O cpf não pode ser nulo.")
    String cpf,
    @NotBlank(message = "A data de nascimento não pode ser nula.")
    LocalDate dataNascimento,
    @NotBlank(message = "O email não pode ser nulo.")
    String email,
    List<TelefoneRequestDTO> telefones,
    List<EnderecoRequestDTO> enderecos
) {}