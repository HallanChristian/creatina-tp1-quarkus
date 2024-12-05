package br.unitins.tp1.creatina.dto.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.unitins.tp1.creatina.model.Boleto;
import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.SituacaoPagamento;

public record BoletoResponseDTO(
    Long id,
    BigDecimal valor,
    LocalDateTime dataPagamento,
    LocalDateTime dataVencimento,
    SituacaoPagamento situacaoPagamento,
    MetodoPagamento metodoPagamento,
    String codigoBarras,
    LocalDate dataValidade,
    String aprovado
) {

    public static BoletoResponseDTO valueOf(Boleto boleto) {
        return new BoletoResponseDTO(
            boleto.getId(),
            boleto.getValor(),
            boleto.getDataPagamento(),
            boleto.getDataVencimento(),
            boleto.getSituacaoPagamento(),
            boleto.getMetodoPagamento(),
            boleto.getCodigoBarras(),
            boleto.getDataValidade(),
            boleto.getAprovado() == true ? "Sim" : "Não"
        );
    }
}

