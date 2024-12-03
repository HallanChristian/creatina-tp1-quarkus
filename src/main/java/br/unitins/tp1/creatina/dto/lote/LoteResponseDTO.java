package br.unitins.tp1.creatina.dto.lote;

import java.time.LocalDate;

import br.unitins.tp1.creatina.dto.creatina.CreatinaResponseDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.creatina.model.Lote;

public record LoteResponseDTO(
    Long id,
    LocalDate dataValidade,
    LocalDate dataFabricacao,
    String codigo,
    Integer estoque,
    CreatinaResponseDTO creatina,
    EnderecoResponseDTO localDistribuicao 
) {

    public static LoteResponseDTO valueOf(Lote lote) {
        return new LoteResponseDTO(
            lote.getId(),
            lote.getDataValidade(),
            lote.getDataFabricacao(),
            lote.getCodigo(),
            lote.getEstoque(),
            CreatinaResponseDTO.valueOf(lote.getCreatina()),
            EnderecoResponseDTO.valueOf(lote.getLocalDistribuicao())
        );
    }
    
}