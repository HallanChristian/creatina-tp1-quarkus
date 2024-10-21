package br.unitins.tp1.creatina.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record FornecedorRequestDTO(
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome, 
    @NotBlank(message = "O cnpj não pode ser nulo.")
    String cnpj,
    List<TelefoneFornecedorRequestDTO> telefones
) {}
