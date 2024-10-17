package br.unitins.tp1.faixas.dto;

import java.util.List;

import br.unitins.tp1.faixas.model.Fornecedor;

public record FornecedorResponseDTO(
    Long id,
    String nome,
    String cnpj, 
    List<TelefoneResponseDTO> telefones,
    List<EnderecoResponseDTO> enderecos) {

    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
        return new FornecedorResponseDTO (
            fornecedor.getId(),
            fornecedor.getNome(), 
            fornecedor.getCnpj(),
            fornecedor.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
            fornecedor.getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList()
            );
    }
    
}
