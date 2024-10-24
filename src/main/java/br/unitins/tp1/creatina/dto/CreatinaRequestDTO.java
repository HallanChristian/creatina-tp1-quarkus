package br.unitins.tp1.creatina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO Creatina
public record CreatinaRequestDTO(
    // Nome da creatina (obrigatório)
    @NotBlank(message = "Informe o nome da creatina.")
    String nome, 

    // Marca da creatina (obrigatório)
    @NotBlank(message = "Informe a marca da creatina.")
    String marca,

    // Quantidade em gramas (obrigatório)
    @NotNull(message = "Informe a quantidade em gramas.")
    double quantidadeEmGramas,

    // Tipo da creatina (obrigatório)
    @NotBlank(message = "Informe o tipo da creatina.")
    String tipo,

    // Preço da creatina (obrigatório)
    @NotNull(message = "Informe o preço da creatina.")
    double preco
) {}
