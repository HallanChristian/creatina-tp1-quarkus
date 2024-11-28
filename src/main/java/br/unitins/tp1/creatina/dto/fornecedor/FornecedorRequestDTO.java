package br.unitins.tp1.creatina.dto.fornecedor;

import java.util.List;

import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.NotBlank;

// DTO Fornecedor
public record FornecedorRequestDTO(
    // Nome do fornecedor (obrigatório)
    @NotBlank(message = "Informe o nome do fornecedor.")
    String nome, 

    // CNPJ do fornecedor (obrigatório)
    @NotBlank(message = "Informe o CNPJ do fornecedor.")
    String cnpj,
    
    List<TelefoneRequestDTO> telefones
) {}
