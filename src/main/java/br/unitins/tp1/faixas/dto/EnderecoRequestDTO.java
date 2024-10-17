package br.unitins.tp1.faixas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDTO(
    @NotBlank(message = "O campo deve ser informado") 
    String cep,
    @NotBlank(message = "O campo deve ser informado") 
    String logradouro,
    @NotBlank(message = "O campo deve ser informado") 
    String numero,
    @NotNull(message = "O campo municipio deve ser informado") 
    Long idMunicipio
) {}
