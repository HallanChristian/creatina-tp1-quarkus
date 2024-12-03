package br.unitins.tp1.creatina.dto.itempedido;

import java.math.BigDecimal;

public record ItemPedidoRequestDTO(
    Long idProduto, 
    Integer quantidade,
    BigDecimal preco) {

}
