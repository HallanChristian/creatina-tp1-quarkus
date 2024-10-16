package br.unitins.tp1.faixas.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatinaRequestDTO(
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome, 
    @NotBlank(message = "A marca não pode ser nula.")
    String marca,
    @NotBlank(message = "A quantidade em gramas não pode ser nula.")
    double quantidadeEmGramas,
    @NotBlank(message = "O tipo não pode ser nulo.")
    String tipo,
    @NotBlank(message = "O preço não pode ser nulo.")
    double preco,
    Integer idSexo
) {}
