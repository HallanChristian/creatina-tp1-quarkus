package br.unitins.tp1.faixas.repository;

import java.util.List;

import br.unitins.tp1.faixas.model.Cliente;
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
    
}
