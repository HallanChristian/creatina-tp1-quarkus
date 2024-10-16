package br.unitins.tp1.faixas.repository;

import java.util.List;

import br.unitins.tp1.faixas.model.Creatina;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreatinaRepository implements PanacheRepository<Creatina> {
    
    public List<Creatina> findByNome(String nome) {
        return find("SELECT p FROM Creatina p WHERE p.nome LIKE ?1", "%" + nome + "%").list();
    }
    
}
