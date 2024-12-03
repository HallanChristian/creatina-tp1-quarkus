package br.unitins.tp1.creatina.dto.fornecedor;

import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO Fornecedor
public record FornecedorRequestDTO(
    // Nome do fornecedor (obrigatório)
    @NotBlank(message = "Informe o nome do fornecedor.")
    String nome, 

    // CNPJ do fornecedor (obrigatório)
    @NotBlank(message = "Informe o CNPJ do fornecedor.")
    String cnpj,
    
    // Lista de telefones do funcionário
    @NotNull(message = "Informe ao menos um telefone do funcionário.")
    List<TelefoneRequestDTO> telefones,

    // Lista de endereços (pode ser vazio, mas não nulo)
    @NotNull(message = "A lista de endereços não pode ser nula.")
    @Size(min = 1, message = "O fornecedor deve ter pelo menos um endereço.")
    List<EnderecoRequestDTO> enderecos
) {}
