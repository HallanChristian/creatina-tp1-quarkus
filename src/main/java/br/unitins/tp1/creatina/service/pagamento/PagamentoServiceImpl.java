package br.unitins.tp1.creatina.service.pagamento;

import java.time.LocalDateTime;
import java.util.UUID;

import br.unitins.tp1.creatina.model.Boleto;
import br.unitins.tp1.creatina.model.Cartao;
import br.unitins.tp1.creatina.model.CartaoPagamento;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.MetodoPagamento;
import br.unitins.tp1.creatina.model.Pagamento;
import br.unitins.tp1.creatina.model.Pedido;
import br.unitins.tp1.creatina.model.Pix;
import br.unitins.tp1.creatina.repository.PagamentoRepository;
import br.unitins.tp1.creatina.service.cartao.CartaoService;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import br.unitins.tp1.creatina.service.pedido.PedidoService;
import br.unitins.tp1.creatina.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    public PagamentoRepository pagamentoRepository;

    @Inject
    public ClienteService clienteService;

    @Inject
    public PedidoService pedidoService;

    @Inject
    public CartaoService cartaoService;

    @Override
    public Pagamento findById(Long id) {
        return pagamentoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pix criarPix(Long idPedido, String username) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();
        validarCliente(username, cliente);
        validarPedidoCliente(pedido, cliente);

        if (pedido.getPagamento() != null) {
            throw new ValidationException("idPedido","O pedido já possui um método de pagamento.");
        }

        Pix pix = new Pix();
        pix.setChave(gerarUUIDPedidoCliente(idPedido, cliente.getId()));
        pix.setDataVencimento(LocalDateTime.now().plusHours(3));
        pix.setValor(pedido.getValorTotal());
        pix.setAprovado(false);
        pix.setMetodoPagamento(MetodoPagamento.PIX);

        pagamentoRepository.persist(pix);
        pedido.setPagamento(pix);

        return pix;
    }

    @Override
    @Transactional
    public Boleto criarBoleto(Long idPedido, String username) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();
        validarCliente(username, cliente);
        validarPedidoCliente(pedido, cliente);

        if (pedido.getPagamento() != null) {
            throw new ValidationException("idPedido","O pedido já possui um método de pagamento.");
        }

        Boleto boleto = new Boleto();
        boleto.setCodigoBarras(gerarUUIDPedidoCliente(idPedido, cliente.getId()));
        boleto.setValor(pedido.getValorTotal());
        boleto.setDataVencimento(LocalDateTime.now().plusDays(3));
        boleto.setAprovado(false);
        boleto.setMetodoPagamento(MetodoPagamento.BOLETO);

        pagamentoRepository.persist(boleto);
        pedido.setPagamento(boleto);

        return boleto;
    }

    @Override
    @Transactional
    public void pagar(Long idPedido, String username, String identificador, MetodoPagamento metodoPagamento) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();

        validarPedidoCliente(pedido, cliente);

        Pagamento pagamento;

        switch (metodoPagamento) {
            case PIX:
                pagamento = pagamentoRepository.findByChavePix(identificador);
                break;
            case BOLETO:
                pagamento = pagamentoRepository.findByCodigoBoleto(identificador);
                break;
            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido.");
        }

        validarPagamentoPedido(pedido, pagamento);

        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setAprovado(true);
        pedidoService.updateEstadoPedido(idPedido, 1);
    }

    @Override
    @Transactional
    public void pagarCartao(Long idPedido, String username, Long idCartao) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();

        validarPedidoCliente(pedido, cliente);

        if (pedido.getPagamento() != null && pedido.getPagamento().getAprovado()) {
            throw new ValidationException("idPedido", "O pedido já foi aprovado.");
        }

        Cartao cartao = cartaoService.findById(idCartao);

        if (!cliente.getCartoes().contains(cartao)) {
            throw new EntityNotFoundException("Cartão não encontrado para o cliente.");
        }

        CartaoPagamento cartaoPagamento = new CartaoPagamento();
        cartaoPagamento.setNumero(cartao.getNumero());
        cartaoPagamento.setCvc(cartao.getCvc());
        cartaoPagamento.setValidade(cartao.getValidade());
        cartaoPagamento.setValor(pedido.getValorTotal());
        cartaoPagamento.setTipoCartao(cartao.getTipoCartao());
        cartaoPagamento.setDataPagamento(LocalDateTime.now());
        cartaoPagamento.setAprovado(true);
        cartaoPagamento.setMetodoPagamento(MetodoPagamento.CARTAO);

        pedido.setPagamento(cartaoPagamento);
        pagamentoRepository.persist(cartaoPagamento);
        pedidoService.updateEstadoPedido(idPedido, 1);
    }

    private void validarPedidoCliente(Pedido pedido, Cliente cliente) {
        if (!pedido.getCliente().equals(cliente)) {
            throw new ValidationException("Cliente","O cliente não corresponde ao pedido.");
        }
    }

    private void validarPagamentoPedido(Pedido pedido, Pagamento pagamento) {
        if (pagamento == null) {
            throw new ValidationException("idPedido","Pagamento não encontrado.");
        }

        if (!pagamento.equals(pedido.getPagamento())) {
            throw new ValidationException("idPedido","Pagamento não corresponde ao pedido.");
        }

        if (pagamento.getAprovado()) {
            throw new ValidationException("identificador", "Pagamento já foi realizado.");
        }

        if (LocalDateTime.now().isAfter(pagamento.getDataVencimento())) {
            throw new ValidationException("idPedido", "O pagamento expirou.");
        }
    }

    private void validarCliente(String username, Cliente cliente) {
        if (!(cliente.equals(clienteService.findByUsername(username)))) {
            throw new ValidationException("idPedido", "Cliente não possui um pedido com esse código");
        }
    }

    private String gerarUUIDPedidoCliente(Long idPedido, Long idCliente) {
        String base = idPedido + "-" + idCliente;
        return UUID.nameUUIDFromBytes(base.getBytes()).toString();
    }
}