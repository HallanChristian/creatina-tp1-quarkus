package br.unitins.tp1.creatina.dto.pagamento;

import java.time.LocalDateTime;

import br.unitins.tp1.creatina.model.Pix;
import br.unitins.tp1.creatina.model.SituacaoPagamento;

public record PixResponseDTO(
    Long id,
    Double valor,
    LocalDateTime dataPagamento,
    LocalDateTime dataVencimento,
    SituacaoPagamento situacaoPagamento,
    String chave
) {

    public static PixResponseDTO valueOf(Pix pix) {
        return new PixResponseDTO(
            pix.getId(),
            pix.getValor(),
            pix.getDataPagamento(),
            pix.getDataVencimento(),
            pix.getSituacaoPagamento(),
            pix.getChave()
        );
    }
}

