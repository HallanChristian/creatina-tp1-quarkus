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

    public List<Telefone> findByFuncionario(Long idFuncionario) {
        return find("funcionario.id = ?1", idFuncionario).list();
    }

    public List<Telefone> findByFornecedor(Long idFornecedor) {
        return find("fornecedor.id = ?1", idFornecedor).list();
    }

    public List<Telefone> findByNumero(String numero) {
        return find("numero = ?1", numero).list();
    }

    public List<Telefone> findByDdd(String ddd) {
        return find("ddd = ?1", ddd).list();
    }
}
