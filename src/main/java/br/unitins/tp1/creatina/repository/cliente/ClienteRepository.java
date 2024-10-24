package br.unitins.tp1.creatina.repository.cliente;

import java.util.List;
import java.util.Map;

import br.unitins.tp1.creatina.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findByNome(String nome) {
        return find("SELECT cliente FROM Cliente cliente WHERE cliente.nome LIKE ?1", "%" + nome + "%").list();
    }

    public List<Cliente> findByCpf(String cpf) {
        return find("cpf LIKE ?1", "%" + cpf + "%").list();
    }

    public void deleteClienteEndereco(Long clienteId, Long enderecoId) {
        delete("DELETE FROM Endereco e WHERE e.cliente.id = :clienteId AND e.id = :enderecoId", 
        Map.of("clienteId", clienteId, "enderecoId", enderecoId));
    }
    
}
