package br.unitins.tp1.creatina.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
    // Nome da categoria (obrigatório)
    @NotBlank(message = "Informe o nome da categoria.")
    String nome,

    // Descrição da categoria (opcional)
    String descricao
) {}

