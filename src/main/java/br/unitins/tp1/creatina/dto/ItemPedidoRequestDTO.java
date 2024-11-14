package br.unitins.tp1.creatina.dto;

public record ItemPedidoRequestDTO(
    Long idProduto, 
    Integer quantidade,
    Double preco) {

}
