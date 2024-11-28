package br.unitins.tp1.creatina.dto.itempedido;

import java.math.BigDecimal;

import br.unitins.tp1.creatina.model.ItemPedido;

public record ItemPedidoResponseDTO(
    Long idLote, 
    String nomeProduto, 
    Integer quantidade,
    BigDecimal valorUnitario) {

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        if (itemPedido == null || itemPedido.getLote() == null || itemPedido.getLote().getCreatina() == null) {
            throw new IllegalArgumentException("Dados do itemPedido ou lote incompletos.");
        }

        return new ItemPedidoResponseDTO(
            itemPedido.getLote().getId(),
            itemPedido.getLote().getCreatina().getNome(),
            itemPedido.getQuantidade(),
            itemPedido.getPreco()
        );
    }

}