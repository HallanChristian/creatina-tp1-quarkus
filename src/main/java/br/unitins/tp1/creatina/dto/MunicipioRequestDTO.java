package br.unitins.tp1.creatina.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO Municipio
public record MunicipioRequestDTO (
    // Nome do Municipio (obrigatório, máximo 60 caracteres)
    @NotBlank(message = "Informe o nome do Municipio.")
    @Size(max = 60, message = "O nome deve conter no máximo 60 caracteres.")
    String nome, 
    
    // ID do estado (obrigatório)
    @NotNull(message = "Informe o ID do estado.")
    Long idEstado
) {

}
