package br.unitins.tp1.creatina.repository;

import java.util.List;

import br.unitins.tp1.creatina.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

    public List<Telefone> findByCliente(Long idCliente) {
        return find("cliente.id = ?1", idCliente).list();
    }

    public void deleteTelefoneByCliente(Long idCliente, Long idTelefone) {
        delete("id = ?1 AND cliente.id = ?2", idTelefone, idCliente);
    }

    public void deleteByCliente(Long idCliente) {
        delete("cliente.id = ?1", idCliente);
    }
}
