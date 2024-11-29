package br.unitins.tp1.creatina.repository;

import br.unitins.tp1.creatina.model.Pagamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public Pagamento findByChavePix(String chave) {
        return find("SELECT p FROM Pagamento p WHERE p.pix.chave = ?1", chave).firstResult();
    }

    public Pagamento findByCodigoBoleto(String codigo) {
        return find("SELECT p FROM Pagamento p WHERE p.boleto.codigoBarras = ?1", codigo).firstResult();
    }
}
