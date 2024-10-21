package br.unitins.tp1.creatina.repository.telefone;

import java.util.List;

import br.unitins.tp1.creatina.model.TelefoneFornecedor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneFornecedorRepository implements PanacheRepository<TelefoneFornecedor> {
    
    public List<TelefoneFornecedor> findByTelefoneFornecedor(Long idTelefoneFornecedor) {
        return find("SELECT telefonefornecedor FROM TelefoneFornecedor telefonefornecedor WHERE telefonefornecedor.fornecedor.id = ?1", idTelefoneFornecedor ).list();
    }
    
}
