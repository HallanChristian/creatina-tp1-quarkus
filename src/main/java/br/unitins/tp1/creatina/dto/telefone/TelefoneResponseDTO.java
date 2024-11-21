package br.unitins.tp1.creatina.dto.telefone;

import br.unitins.tp1.creatina.model.Telefone;

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
