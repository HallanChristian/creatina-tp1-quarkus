package br.unitins.tp1.creatina.service.pedido;

import java.util.List;

import br.unitins.tp1.creatina.dto.PedidoRequestDTO;
import br.unitins.tp1.creatina.model.Pedido;


public interface PedidoService {

    Pedido findById(Long id);

    List<Pedido> findByUsername(String username);

    Pedido create(PedidoRequestDTO dto, String username);

    // implementar os patch's

    // pensar no cancelar 

}
