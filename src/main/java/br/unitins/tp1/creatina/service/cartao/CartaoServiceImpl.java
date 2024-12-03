package br.unitins.tp1.creatina.service.cartao;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.creatina.dto.pagamento.CartaoRequestDTO;
import br.unitins.tp1.creatina.model.Cartao;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.repository.CartaoRepository;
import br.unitins.tp1.creatina.service.cliente.ClienteService;
import br.unitins.tp1.creatina.service.hash.HashService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CartaoServiceImpl implements CartaoService {

    @Inject
    public CartaoRepository cartaoRepository;

    @Inject
    public ClienteService clienteService;

    @Inject
    public HashService hashService;


    @Override
    public Cartao findById(Long id) {
        return cartaoRepository.findById(id);
    }

    @Override
    public List<Cartao> findByCliente(String username) {
        return clienteService.findByUsername(username).getCartoes();
    }

    @Override
    public List<Cartao> findAll() {
        return cartaoRepository.findAll().list();
    }

    @Override
    @Transactional
    public Cartao create(String username, CartaoRequestDTO dto) {
        Cliente cliente = clienteService.findByUsername(username);
        Cartao cartao = new Cartao();

        cartao.setNumero(dto.numero());
        cartao.setCvc(dto.cvc());
        cartao.setValidade(dto.validade());
        cartao.setNomeTitular(dto.nomeTitular());
        cartao.setTipoCartao(dto.tipoCartao());
        cartaoAddCliente(cliente, cartao);

        cartaoRepository.persist(cartao);

        return cartao;
    }

    private void cartaoAddCliente(Cliente cliente, Cartao cartao) {
        if (cliente.getCartoes() == null) {
            cliente.setCartoes(new ArrayList<>());
        }

        cliente.getCartoes().add(cartao);

    }

    @Override
    @Transactional
    public void update(String username, Long id, CartaoRequestDTO dto) {
        Cartao cartao = cartaoRepository.findById(id);
        
        cartao.setNumero(dto.numero());
        cartao.setCvc(dto.cvc());
        cartao.setValidade(dto.validade());
        cartao.setNomeTitular(dto.nomeTitular());
        cartao.setTipoCartao(dto.tipoCartao());

    }

    @Override
    @Transactional
    public void delete(String username, Long id) {
        Cliente cliente = clienteService.findByUsername(username);
        Cartao cartao = cartaoRepository.findById(id);
        
        cliente.getCartoes().remove(cartao);
    }
}