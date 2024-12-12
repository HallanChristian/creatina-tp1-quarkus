package br.unitins.tp1.creatina.service.pagamento;

import br.unitins.tp1.creatina.model.Boleto;
import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.Pagamento;
import br.unitins.tp1.creatina.model.Pix;


public interface PagamentoService {

    Pagamento findById(Long id);

    Pix criarPix(Long idPedido, String username);
    
    Boleto criarBoleto(Long idPedido, String username);

    void pagar(Long idPedido, String username, String identificador, MetodoPagamento metodoPagamento);

    void pagarCartao(Long idPedido, String username, Long idCartao);
}
