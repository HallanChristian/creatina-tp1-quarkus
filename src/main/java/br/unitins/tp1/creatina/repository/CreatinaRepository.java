package br.unitins.tp1.creatina.repository;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.creatina.model.Creatina;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreatinaRepository implements PanacheRepository<Creatina> {
    
    public List<Creatina> findByNome(String nome) {
        return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
    }

    public List<Creatina> findByMarca(String marca) {
        return find("LOWER(marca) LIKE LOWER(?1)", "%" + marca + "%").list();
    }

    public List<Creatina> findByTipo(String tipo) {
        return find("LOWER(tipo) LIKE LOWER(?1)", "%" + tipo + "%").list();
    }

    public List<Creatina> findByPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return find("preco BETWEEN ?1 AND ?2", precoMin, precoMax).list();
    }

    public List<Creatina> findByFilters(String nome, String marca, String tipo, BigDecimal precoMin, BigDecimal precoMax) {
        String query = "1=1";
        if (nome != null && !nome.isBlank()) {
            query += " AND LOWER(nome) LIKE LOWER('%" + nome + "%')";
        }
        if (marca != null && !marca.isBlank()) {
            query += " AND LOWER(marca) LIKE LOWER('%" + marca + "%')";
        }
        if (tipo != null && !tipo.isBlank()) {
            query += " AND LOWER(tipo) LIKE LOWER('%" + tipo + "%')";
        }
        if (precoMin != null && precoMax != null) {
            query += " AND preco BETWEEN " + precoMin + " AND " + precoMax;
        }
        return find(query).list();
    }
    
}
