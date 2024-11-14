package br.unitins.tp1.creatina.dto;

import java.time.LocalDate;

import br.unitins.tp1.creatina.model.Lote;

public record LoteResponseDTO(
    Long id,
    LocalDate data,
    String codigo,
    Integer estoque,
    CreatinaResponseDTO creatina 
) {

    public static LoteResponseDTO valueOf(Lote lote) {
        return new LoteResponseDTO(
            lote.getId(),
            lote.getData(),
            lote.getCodigo(),
            lote.getEstoque(),
            CreatinaResponseDTO.valueOf(lote.getCreatina())
        );
    }
    
}