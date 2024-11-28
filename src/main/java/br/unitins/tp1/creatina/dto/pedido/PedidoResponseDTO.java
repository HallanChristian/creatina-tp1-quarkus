package br.unitins.tp1.creatina.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.creatina.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.creatina.model.Pedido;

public record PedidoResponseDTO(
    Long id,
    LocalDateTime data,
    Double valorTotal,
    List<ItemPedidoResponseDTO> listaItemPedido 
) {

    public static PedidoResponseDTO valueOf(Pedido pedido) {
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getData(),
            pedido.getValorTotal(),
            pedido.getListaItemPedido().stream().map(i -> ItemPedidoResponseDTO.valueOf(i)).toList()  
        );

    }

}
