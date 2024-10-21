package br.unitins.tp1.creatina.service.telefone;

import java.util.List;

import br.unitins.tp1.creatina.dto.TelefoneFornecedorRequestDTO;
import br.unitins.tp1.creatina.model.TelefoneFornecedor;


public interface TelefoneFornecedorService {

    TelefoneFornecedor findById(Long id);

    List<TelefoneFornecedor> findByFornecedor(Long id);

    List<TelefoneFornecedor> findAll();

    TelefoneFornecedor create(TelefoneFornecedorRequestDTO dto, Long id);

    TelefoneFornecedor update(Long id, TelefoneFornecedorRequestDTO dto);

    void delete(Long id); 
    
}
