package br.unitins.tp1.creatina.service.pedido;

import java.util.List;

import br.unitins.tp1.creatina.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.creatina.model.Pedido;


public interface PedidoService {

    Pedido findById(Long id);

    List<Pedido> findByUsername(String username);

    Pedido create(PedidoRequestDTO dto, String username);

    Pedido detalhesPedido(Long id, String username);

    // Implementar os patch's

    void updateEstadoPedido(Long id, Integer novaSituacaoId);

    void cancelarPedido(String username, Long id);

    void retornarPedido(String username, Long id);

    // pensar no cancelar 

}
