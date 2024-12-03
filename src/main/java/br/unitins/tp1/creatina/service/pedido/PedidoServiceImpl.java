package br.unitins.tp1.creatina.service.pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.itempedido.ItemPedidoRequestDTO;
import br.unitins.tp1.creatina.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.EstadoPedido;
import br.unitins.tp1.creatina.model.ItemPedido;
import br.unitins.tp1.creatina.model.Lote;
import br.unitins.tp1.creatina.model.Pedido;
import br.unitins.tp1.creatina.model.SituacaoPedido;
import br.unitins.tp1.creatina.repository.PedidoRepository;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import br.unitins.tp1.creatina.service.lote.LoteService;
import br.unitins.tp1.creatina.service.usuario.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    public PedidoRepository pedidoRepository;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public LoteService loteService;

    @Inject
    public ClienteService clienteService;

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> findByUsername(String username) {
        Cliente cliente = clienteService.findByUsername(username);
        return pedidoRepository.findByCliente(cliente.getId());
    }

    @Override
    @Transactional
    public Pedido create(PedidoRequestDTO dto, String username) {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());

        // Obter cliente associado ao username
        Cliente cliente = clienteService.findByUsername(username);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o username fornecido.");
        }
        pedido.setCliente(cliente);

        // Inicializa lista de itens do pedido
        pedido.setListaItemPedido(new ArrayList<>());
        BigDecimal totalCalculado = BigDecimal.ZERO;

        // Processa itens do pedido
        for (ItemPedidoRequestDTO itemDTO : dto.listaItemPedido()) {
            Lote lote = loteService.findByIdCreatina(itemDTO.idProduto());
            if (lote == null) {
                throw new IllegalArgumentException("Produto não encontrado para o ID fornecido: " + itemDTO.idProduto());
            }

            if (lote.getEstoque() < itemDTO.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + lote.getCreatina().getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setLote(lote);
            item.setQuantidade(itemDTO.quantidade());

            // Valida o preço informado pelo cliente
            BigDecimal precoEsperado = lote.getCreatina().getPreco();
            if (precoEsperado.compareTo(itemDTO.preco()) != 0) {
                throw new IllegalArgumentException("Preço divergente para o produto: " + lote.getCreatina().getNome() +
                        ". Esperado: " + precoEsperado + ", Informado: " + itemDTO.preco());
            }
            item.setPreco(itemDTO.preco());

            // Atualiza estoque do lote
            lote.setEstoque(lote.getEstoque() - itemDTO.quantidade());

            // Calcula subtotal e adiciona ao total
            BigDecimal subtotal = item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
            totalCalculado = totalCalculado.add(subtotal);

            pedido.getListaItemPedido().add(item);
        }

        // Valida o valor total calculado
        if (totalCalculado.compareTo(dto.valorTotal()) != 0) {
            throw new IllegalArgumentException("O valor total informado não corresponde ao valor calculado. " +
                    "Calculado: " + totalCalculado + ", Informado: " + dto.valorTotal());
        }
        pedido.setValorTotal(totalCalculado.setScale(2, RoundingMode.HALF_UP));

        // Persiste o pedido no repositório
        pedidoRepository.persist(pedido);

        return pedido;
    }

    @Override
    public Pedido detalhesPedido(Long id, String username) {
        Pedido pedido = findById(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado para o ID fornecido: " + id);
        }
        if (!pedido.getCliente().getUsuario().getUsername().equals(username)) {
            throw new SecurityException("O cliente não tem permissão para acessar este pedido.");
        }
        return pedido;
    }

    @Override
    @Transactional
    public void updateEstadoPedido(Long id, Integer novaSituacaoId) {
        Pedido pedido = findById(id);

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado para o ID fornecido: " + id);
        }

        // Obtém a situação atual e valida a transição
        SituacaoPedido situacaoAtual = pedido.getEstadoPedidos()
                                            .get(pedido.getEstadoPedidos().size() - 1)
                                            .getSituacaoPedido();
        SituacaoPedido novaSituacao = SituacaoPedido.valueOf(novaSituacaoId);

        if (!isTransicaoValida(situacaoAtual.getId(), novaSituacao.getId())) {
            throw new IllegalStateException("Transição de estado inválida: " + situacaoAtual.getLabel() +
                    " -> " + novaSituacao.getLabel());
        }

        // Adiciona o novo estado ao histórico
        pedido.getEstadoPedidos().add(new EstadoPedido(novaSituacao, LocalDateTime.now()));
        pedidoRepository.persist(pedido);
    }

    @Override
    @Transactional
    public void cancelarPedido(String username, Long id) {
        Pedido pedido = detalhesPedido(id, username);

        // Verifica se o pedido pode ser cancelado
        SituacaoPedido situacaoAtual = pedido.getEstadoPedidos()
                                            .get(pedido.getEstadoPedidos().size() - 1)
                                            .getSituacaoPedido();

        if (situacaoAtual.getId().equals(SituacaoPedido.ENTREGUE.getId())) {
            throw new IllegalStateException("Não é possível cancelar um pedido já entregue.");
        }

        // Atualiza estado para CANCELADO
        updateEstadoPedido(id, SituacaoPedido.CANCELADO.getId());
    }

    @Override
    @Transactional
    public void retornarPedido(String username, Long id) {
        Pedido pedido = detalhesPedido(id, username);

        // Verifica se o pedido foi cancelado
        SituacaoPedido situacaoAtual = pedido.getEstadoPedidos()
                                            .get(pedido.getEstadoPedidos().size() - 1)
                                            .getSituacaoPedido();

        if (!situacaoAtual.getId().equals(SituacaoPedido.CANCELADO.getId())) {
            throw new IllegalStateException("Somente pedidos cancelados podem ser retornados.");
        }

        // Retorna pedido ao estado inicial
        updateEstadoPedido(id, SituacaoPedido.PAGAMENTO_EM_ESPERA.getId());
    }

    /**
     * Verifica se a transição de estado do pedido é válida usando IDs.
     */
    private boolean isTransicaoValida(Integer idSituacaoAtual, Integer idNovaSituacao) {
        return switch (idSituacaoAtual) {
            case 4 -> idNovaSituacao == 1 || idNovaSituacao == 5; // PAGAMENTO_EM_ESPERA -> ENVIADO ou CANCELADO
            case 1 -> idNovaSituacao == 2 || idNovaSituacao == 5; // ENVIADO -> ENTREGUE ou CANCELADO
            case 5 -> idNovaSituacao == 4; // CANCELADO -> PAGAMENTO_EM_ESPERA
            case 2, 3 -> false; // ENTREGUE ou DEVOLVIDO não têm transições válidas
            default -> throw new IllegalArgumentException("Situação desconhecida.");
        };
    }
}