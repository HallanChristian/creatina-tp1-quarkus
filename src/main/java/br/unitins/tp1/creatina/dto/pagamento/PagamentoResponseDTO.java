package br.unitins.tp1.creatina.dto.pagamento;

import br.unitins.tp1.creatina.model.Boleto;
import br.unitins.tp1.creatina.model.CartaoPagamento;
import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.Pagamento;
import br.unitins.tp1.creatina.model.Pix;

public record PagamentoResponseDTO(Object Pagamento) {

    public static Object valueOf(Pagamento pagamento) {
        if (pagamento instanceof Pix) {
            Pix pix = (Pix) pagamento;
            return PixResponseDTO.valueOf(pix);
        }

        if (pagamento instanceof Boleto) {
            Boleto boleto = (Boleto) pagamento;
            return BoletoResponseDTO.valueOf(boleto);
        }

        if (pagamento instanceof CartaoPagamento) {
            CartaoPagamento cartao = (CartaoPagamento) pagamento;
            return CartaoPagamentoResponseDTO.valueOf(cartao);
        }

        return MetodoPagamento.NAO_EFETUADO;
    }
}

