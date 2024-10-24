package br.unitins.tp1.creatina.dto;

import jakarta.validation.constraints.NotBlank;

// DTO Endereço
public record EnderecoRequestDTO(
    // Estado (obrigatório)
    @NotBlank(message = "Informe o estado.") 
    String estado,
    
    // Cidade (obrigatório)
    @NotBlank(message = "Informe a cidade.") 
    String cidade,

    // CEP (obrigatório)
    @NotBlank(message = "Informe o CEP.") 
    String cep,

    // Logradouro (obrigatório)
    @NotBlank(message = "Informe o logradouro.") 
    String logradouro,

    // Número (obrigatório)
    @NotBlank(message = "Informe o número.") 
    String numero
) {}
