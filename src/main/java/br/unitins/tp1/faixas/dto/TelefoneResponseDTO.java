package br.unitins.tp1.faixas.dto;

import br.unitins.tp1.faixas.model.Telefone;

public record TelefoneResponseDTO(
    Long id,
    String ddd,
    String numero) {

    public static TelefoneResponseDTO valueOf(Telefone telefone) {
        return new TelefoneResponseDTO (
            telefone.getId(),
            telefone.getDdd(), 
            telefone.getNumero()
            );
    }
    
}
