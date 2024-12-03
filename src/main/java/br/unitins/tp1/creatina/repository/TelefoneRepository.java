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

    public void deleteTelefoneByCliente(Long idCliente, Long idTelefone) {
        delete("id = ?1 AND cliente.id = ?2", idTelefone, idCliente);
    }

    public void deleteTelefoneByFuncionario(Long idFuncionario, Long idTelefone) {
        delete("id = ?1 AND funcionario.id = ?2", idTelefone, idFuncionario);
    }

    public void deleteTelefoneByFornecedor(Long idFornecedor, Long idTelefone) {
        delete("id = ?1 AND fornecedor.id = ?2", idTelefone, idFornecedor);
    }

    public void deleteByCliente(Long idCliente) {
        delete("cliente.id = ?1", idCliente);
    }


    public void deleteByFuncionario(Long idFuncionario) {
        delete("funcionario.id", idFuncionario);
    }

    public void deleteByFornecedor(Long idFornecedor) {
        delete("fornecedor.id", idFornecedor);
    }
}
