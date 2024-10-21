package br.unitins.tp1.creatina.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
    @NotBlank(message = "O campo deve ser informado") 
    String cep,
    @NotBlank(message = "O campo deve ser informado") 
    String logradouro,
    @NotBlank(message = "O campo deve ser informado") 
    String numero,
    @NotBlank(message = "O campo cidade deve ser informado") 
    String cidade,
    @NotBlank(message = "O campo estado deve ser informado") 
    String estado
) {}
