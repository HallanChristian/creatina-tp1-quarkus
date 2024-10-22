package br.unitins.tp1.creatina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatinaRequestDTO(
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome, 
    @NotBlank(message = "A marca não pode ser nula.")
    String marca,
    @NotNull(message = "A quantidade em gramas não pode ser nula.")
    double quantidadeEmGramas,
    @NotBlank(message = "O tipo não pode ser nulo.")
    String tipo,
    @NotNull(message = "O preço não pode ser nulo.")
    double preco
) {}