package br.unitins.tp1.creatina.dto.pagamento;

import java.time.LocalDate;

import br.unitins.tp1.creatina.model.CartaoPagamento;
import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.TipoCartao;

public record CartaoPagamentoResponseDTO(
    Long id,
    MetodoPagamento metodoPagamento,
    String numero,
    String nomeTitular,
    LocalDate validade,
    String cvc,
    TipoCartao tipoCartao,
    String Aprovado
) {

    public static CartaoPagamentoResponseDTO valueOf(CartaoPagamento cartao) {
        return new CartaoPagamentoResponseDTO(
            cartao.getId(),
            cartao.getMetodoPagamento(),
            cartao.getNumero(),
            cartao.getNomeTitular(),
            cartao.getValidade(),
            cartao.getCvc(),
            cartao.getTipoCartao(),
            cartao.getAprovado() == true ? "Sim" : "Não"
        );
    }
}

