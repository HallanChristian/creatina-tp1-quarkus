package br.unitins.tp1.creatina.repository.pessoafisica;

import java.util.List;

import br.unitins.tp1.creatina.model.PessoaFisica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaFisicaRepository implements PanacheRepository<PessoaFisica> {
    
    public List<PessoaFisica> findByNome(String nome) {
        return find("SELECT p FROM PessoaFisica p WHERE p.nome LIKE ?1", "%" + nome + "%").list();
    }
    
}
