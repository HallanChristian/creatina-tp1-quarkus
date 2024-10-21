package br.unitins.tp1.creatina.repository.endereco;

import java.util.List;

import br.unitins.tp1.creatina.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCliente(Long idCliente ){
        return find("SELECT endereco FROM Endereco endereco Where endereco.cliente.id = ?1", idCliente ).list();
        
    }

    public void deleteClienteEndereco(Long idCliente) {
        delete("DELETE FROM cliente_endereco WHERE cliente_id = ?1", idCliente);
    }

    public void deleteByCliente(long idCliente){
        delete("DELETE FROM Endereco WHERE cliente.id = ?1", idCliente);

    }
}
