package br.unitins.tp1.creatina.dto.pedido;

import java.util.List;

import br.unitins.tp1.creatina.dto.itempedido.ItemPedidoRequestDTO;

public record PedidoRequestDTO (
    Double valorTotal, 
    List<ItemPedidoRequestDTO> listaItemPedido
) {

}
