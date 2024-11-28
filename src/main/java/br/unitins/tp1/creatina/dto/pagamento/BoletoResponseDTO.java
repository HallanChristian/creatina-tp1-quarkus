package br.unitins.tp1.creatina.dto.pagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.unitins.tp1.creatina.model.Boleto;
import br.unitins.tp1.creatina.model.SituacaoPagamento;

public record BoletoResponseDTO(
    Long id,
    Double valor,
    LocalDateTime dataPagamento,
    LocalDateTime dataVencimento,
    SituacaoPagamento situacaoPagamento,
    String codigoBarras,
    LocalDate dataValidade
) {

    public static BoletoResponseDTO valueOf(Boleto boleto) {
        return new BoletoResponseDTO(
            boleto.getId(),
            boleto.getValor(),
            boleto.getDataPagamento(),
            boleto.getDataVencimento(),
            boleto.getSituacaoPagamento(),
            boleto.getCodigoBarras(),
            boleto.getDataValidade()
        );
    }
}

