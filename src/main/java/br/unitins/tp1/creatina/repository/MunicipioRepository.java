package br.unitins.tp1.creatina.repository;

import java.util.List;

import br.unitins.tp1.creatina.model.Estado;
import br.unitins.tp1.creatina.model.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {
    
    public List<Municipio> findByNome(String nome) {
        return find("nome LIKE ?1", "%" + nome + "%").list();
    }

    public List<Municipio> findByEstado(Estado estado) {
        return find("estado.id = ?1", estado.getId()).list(); 
    }
    
}
