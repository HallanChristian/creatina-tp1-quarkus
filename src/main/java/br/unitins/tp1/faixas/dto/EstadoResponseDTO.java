package br.unitins.tp1.faixas.dto;

import br.unitins.tp1.faixas.model.Estado;

public record EstadoResponseDTO(Long id, String nome, String sigla) {

    public static EstadoResponseDTO valueOf(Estado estado) {
        return new EstadoResponseDTO (
            estado.getId(), 
            estado.getNome(), 
            estado.getSigla());
    }
    
}
