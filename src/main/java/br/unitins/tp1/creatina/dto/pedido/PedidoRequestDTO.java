package br.unitins.tp1.creatina.dto.pedido;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.creatina.dto.itempedido.ItemPedidoRequestDTO;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO (
    @NotNull(message = "O campo valor total deve ser informado") BigDecimal valorTotal, 
    List<ItemPedidoRequestDTO> listaItemPedido,
    Long idEnderecoEntrega
) {

}
