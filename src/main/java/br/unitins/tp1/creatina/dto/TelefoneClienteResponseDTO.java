package br.unitins.tp1.creatina.dto;

import br.unitins.tp1.creatina.model.TelefoneCliente;

public record TelefoneClienteResponseDTO(
    Long id,
    String ddd,
    String numero) {

    public static TelefoneClienteResponseDTO valueOf(TelefoneCliente telefone) {
        return new TelefoneClienteResponseDTO (
            telefone.getId(),
            telefone.getDdd(), 
            telefone.getNumero()
            );
    }
    
}
