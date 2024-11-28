package br.unitins.tp1.creatina.dto.itempedido;

public record ItemPedidoRequestDTO(
    Long idProduto, 
    Integer quantidade,
    Double preco) {

}
