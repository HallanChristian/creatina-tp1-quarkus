package br.unitins.tp1.creatina.repository;

import java.util.List;

import br.unitins.tp1.creatina.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    // Busca clientes pelo nome (com suporte a busca parcial)
    public List<Cliente> findByNome(String nome) {
        return find("nome LIKE ?1", "%" + nome + "%").list();
    }

    // Busca cliente pelo username
    public Cliente findByUsername(String username) {
        return find("usuario.username = ?1", username).firstResult();
    }

    // Busca cliente pelo CPF
    public Cliente findByCpf(String cpf) {
        return find("cpf = ?1", cpf).firstResult();
    }

    // Busca cliente pelo email
    public Cliente findByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }
    
}
