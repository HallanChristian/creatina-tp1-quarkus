package br.unitins.tp1.creatina.dto;

import br.unitins.tp1.creatina.model.TelefoneFornecedor;

public record TelefoneFornecedorResponseDTO(
    Long id,
    String ddd,
    String numero) {

    public static TelefoneFornecedorResponseDTO valueOf(TelefoneFornecedor telefone) {
        return new TelefoneFornecedorResponseDTO (
            telefone.getId(),
            telefone.getDdd(), 
            telefone.getNumero()
            );
    }
    
}
