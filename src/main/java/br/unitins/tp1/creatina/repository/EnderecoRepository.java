package br.unitins.tp1.creatina.repository;

import java.util.List;

import br.unitins.tp1.creatina.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCliente(Long idCliente) {
        return find("cliente.id = ?1", idCliente).list();
    }

    public void deleteEnderecoByCliente(Long idCliente, Long idEndereco) {
        delete("id = ?1 AND cliente.id = ?2", idEndereco, idCliente);
    }

    public void deleteByCliente(Long idCliente) {
        delete("cliente.id = ?1", idCliente);
    }
}
