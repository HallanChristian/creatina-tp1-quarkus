package br.unitins.tp1.creatina.dto.pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.Pix;
import br.unitins.tp1.creatina.model.SituacaoPagamento;

public record PixResponseDTO(
    Long id,
    BigDecimal valor,
    LocalDateTime dataPagamento,
    LocalDateTime dataVencimento,
    SituacaoPagamento situacaoPagamento,
    MetodoPagamento metodoPagamento,
    String chave,
    String Aprovado
) {

    public static PixResponseDTO valueOf(Pix pix) {
        return new PixResponseDTO(
            pix.getId(),
            pix.getValor(),
            pix.getDataPagamento(),
            pix.getDataVencimento(),
            pix.getSituacaoPagamento(),
            pix.getMetodoPagamento(),
            pix.getChave(),
            pix.getAprovado() == true ? "Sim" : "Não"
        );
    }
}

