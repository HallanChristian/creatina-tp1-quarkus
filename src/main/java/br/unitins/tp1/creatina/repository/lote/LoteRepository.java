package br.unitins.tp1.creatina.repository.lote;

import br.unitins.tp1.creatina.model.Lote;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoteRepository implements PanacheRepository<Lote> {
    
    /**
     * @return retorna a creatina com o lote mais antigo e com quantidade maior que zero
     */
    public Lote findByIdCreatina(Long idCreatina) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("SELECT ");
        jpql.append("  l ");
        jpql.append("FROM ");
        jpql.append("  Lote l ");
        jpql.append("WHERE ");
        jpql.append("  l.creatina.id = ?1 ");
        jpql.append("  AND l.estoque > 0 ");
        jpql.append("ORDER BY l.data ");

        return find(jpql.toString(), idCreatina).firstResult();
    }
  
    public Lote findByCodigo(String codigo) {
        StringBuffer jpql = new StringBuffer();
        jpql.append("SELECT ");
        jpql.append("  l ");
        jpql.append("FROM ");
        jpql.append("  Lote l ");
        jpql.append("WHERE ");
        jpql.append("  l.codigo = ?1 ");

        return find(jpql.toString(), codigo).firstResult();
    }
    
}
