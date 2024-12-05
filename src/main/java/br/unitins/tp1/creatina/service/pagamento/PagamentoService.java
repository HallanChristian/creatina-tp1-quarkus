package br.unitins.tp1.creatina.service.pagamento;

import br.unitins.tp1.creatina.model.Boleto;
import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.Pagamento;
import br.unitins.tp1.creatina.model.Pix;


public interface PagamentoService {

    Pagamento findById(Long id);

    Pix criarPix(Long idPedido, Long idCliente);
    
    Boleto criarBoleto(Long idPedido, Long idCliente);

    void pagar(Long idPedido, Long idCliente, String identificador, MetodoPagamento metodoPagamento);

    void pagarCartao(Long idPedido, Long idCliente, Long idCartao);
}