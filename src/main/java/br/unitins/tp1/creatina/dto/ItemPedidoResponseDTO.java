package br.unitins.tp1.creatina.dto;

import br.unitins.tp1.creatina.model.ItemPedido;

public record ItemPedidoResponseDTO(
    Long idProduto, 
    String nome, 
    Integer quantidade,
    Double valor) {

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO (
            itemPedido.getLote().getCreatina().getId(), 
            itemPedido.getLote().getCreatina().getNome(), 
            itemPedido.getQuantidade(),
            itemPedido.getPreco());
    }

}