package br.unitins.tp1.creatina.service.cartao;

import java.util.List;

import br.unitins.tp1.creatina.dto.pagamento.CartaoRequestDTO;
import br.unitins.tp1.creatina.model.Cartao;

public interface CartaoService {

    Cartao findById(Long id);

    List<Cartao> findByCliente(String username);

    List<Cartao> findAll();

    Cartao create(String username, CartaoRequestDTO dto);

    void update(String username, Long idCartao, CartaoRequestDTO dto);

    void delete(String username, Long id);
    
}
