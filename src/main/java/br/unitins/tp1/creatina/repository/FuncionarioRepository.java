package br.unitins.tp1.creatina.repository;

import java.util.List;

import br.unitins.tp1.creatina.model.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {
    
    public List<Funcionario> findByNome(String nome) {
        return find("nome LIKE ?1", "%" + nome + "%").list();
    }

    public Funcionario findByUsername(String username) {
        return find("usuario.username = ?1", username).firstResult();
    }

    public Funcionario findByCpf(String cpf) {
        return find("cpf = ?1", cpf).firstResult();
    }

    public Funcionario findByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }
}
