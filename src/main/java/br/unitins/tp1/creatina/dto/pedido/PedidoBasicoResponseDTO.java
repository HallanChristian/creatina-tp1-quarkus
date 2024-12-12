package br.unitins.tp1.creatina.dto.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.Pedido;
import br.unitins.tp1.creatina.model.SituacaoPedido;

public record PedidoBasicoResponseDTO(
                Long id,
                LocalDateTime data,
                BigDecimal valorTotal,
                SituacaoPedido situacaoPedido,
                MetodoPagamento metodoPagamento) {

        public static PedidoBasicoResponseDTO valueOf(Pedido pedido) {
                return new PedidoBasicoResponseDTO(
                                pedido.getId(),
                                pedido.getData(),
                                pedido.getValorTotal(),
                                pedido.getEstadoPedidos().getLast().getSituacaoPedido(),
                                pedido.getPagamento() != null ? pedido.getPagamento().getMetodoPagamento()
                                                : MetodoPagamento.NAO_EFETUADO);
        }
}
