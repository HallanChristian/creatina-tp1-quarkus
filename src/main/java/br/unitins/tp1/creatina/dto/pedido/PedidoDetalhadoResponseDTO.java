package br.unitins.tp1.creatina.dto.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.creatina.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.creatina.dto.pagamento.PagamentoResponseDTO;
import br.unitins.tp1.creatina.model.EstadoPedido;
import br.unitins.tp1.creatina.model.Pedido;

public record PedidoDetalhadoResponseDTO(
    Long id,
    LocalDateTime data,
    BigDecimal valorTotal,
    List<ItemPedidoResponseDTO> listaItemPedido,
    List<EstadoPedido> estadoPedidos,
    Object pagamento
) {

    public static PedidoDetalhadoResponseDTO valueOf(Pedido pedido) {
        return new PedidoDetalhadoResponseDTO(
            pedido.getId(),
            pedido.getData(),
            pedido.getValorTotal(),
            pedido.getListaItemPedido().stream().map(i -> ItemPedidoResponseDTO.valueOf(i)).toList(),
            pedido.getEstadoPedidos(),
            PagamentoResponseDTO.valueOf(pedido.getPagamento()));

    }

}
