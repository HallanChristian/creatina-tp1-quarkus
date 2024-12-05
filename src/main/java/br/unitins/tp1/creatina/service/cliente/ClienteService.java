package br.unitins.tp1.creatina.service.cliente;

import java.util.List;

import br.unitins.tp1.creatina.dto.cliente.ClienteRequestDTO;
import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Cliente;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.model.Endereco;
import br.unitins.tp1.creatina.model.Telefone;


public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

    Cliente findByCpf(String cpf);

    Cliente findByEmail(String email);

    Cliente findByUsername(String username);

    List<Cliente> findAll();

    Cliente create(String username, ClienteRequestDTO dto);

    Cliente update(Long id, ClienteRequestDTO dto);

    void delete(Long id);

    Endereco addEndereco(Long clienteId, EnderecoRequestDTO dto);

    void updateEndereco(String username, Long idEndereco, EnderecoRequestDTO dto);

    Telefone addTelefone(Long clienteId, TelefoneRequestDTO dto);

    void updateTelefone(String username, Long idTelefone, TelefoneRequestDTO dto);

    void adicionarListaDesejo(String username, Long idProduto);

    void removerListaDesejo(String username, Long idProduto);

    List<Creatina> getListaDesejos(String username);

}
