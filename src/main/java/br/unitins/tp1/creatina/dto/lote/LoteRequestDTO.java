package br.unitins.tp1.creatina.dto.lote;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoteRequestDTO(
    @NotNull(message = "Informe o ID da creatina.") 
    Long idCreatina, 
    
    @NotNull(message = "Informe a data de fabricação.")
    LocalDate dataFabricacao,     
    
    @NotNull(message = "Informe a data de validade.") 
    LocalDate dataValidade,      
    
    @NotBlank(message = "Informe o código.") 
    String codigo,                
    
    @NotNull(message = "Informe o estoque.") 
    Integer estoque, 
    
    @NotNull(message = "Informe o ID do endereço.") 
    Long idEndereco
) {}
