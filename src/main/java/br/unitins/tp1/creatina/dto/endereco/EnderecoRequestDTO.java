package br.unitins.tp1.creatina.dto.endereco;

import jakarta.validation.constraints.NotBlank;

// DTO Endereço
public record EnderecoRequestDTO(
    // Estado (obrigatório)
    @NotBlank(message = "Informe o estado.") 
    String estado,
    
    // Municipio (obrigatório)
    @NotBlank(message = "Informe a municipio.") 
    Long idMunicipio,

    // CEP (obrigatório)
    @NotBlank(message = "Informe o CEP.") 
    String cep,

    // Logradouro (obrigatório)
    @NotBlank(message = "Informe o logradouro.") 
    String logradouro,

    // Número (obrigatório)
    @NotBlank(message = "Informe o número.") 
    String numero,

    // Bairro (obrigatório)
    @NotBlank(message = "Informe o bairro.") 
    String bairro,

    // Complemento 
    String complemento
) {}
