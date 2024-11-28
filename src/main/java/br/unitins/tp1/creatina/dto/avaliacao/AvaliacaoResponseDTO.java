package br.unitins.tp1.creatina.dto.avaliacao;

import java.time.LocalDateTime;

import br.unitins.tp1.creatina.model.Avaliacao;

public record AvaliacaoResponseDTO(
    Long id,
    Integer estrelas,
    String comentario,
    LocalDateTime dataAvaliacao,
    Long idCliente,
    Long idCreatina
) {

    public static AvaliacaoResponseDTO valueOf(Avaliacao avaliacao) {
        return new AvaliacaoResponseDTO(
            avaliacao.getId(),
            avaliacao.getEstrelas(),
            avaliacao.getComentario(),
            avaliacao.getDataAvaliacao(),
            avaliacao.getCliente().getId(),
            avaliacao.getCreatina().getId()
        );
    }
}
