package br.unitins.tp1.creatina.dto.fornecedor;

import java.util.List;

import br.unitins.tp1.creatina.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.creatina.model.Fornecedor;

public record FornecedorResponseDTO(
    Long id,
    String nome,
    String cnpj, 
    List<TelefoneResponseDTO> telefones) {

    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
        return new FornecedorResponseDTO (
            fornecedor.getId(),
            fornecedor.getNome(), 
            fornecedor.getCnpj(),
            fornecedor.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList()
            );
    }
    
}
