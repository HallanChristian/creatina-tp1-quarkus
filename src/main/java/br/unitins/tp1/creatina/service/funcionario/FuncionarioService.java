package br.unitins.tp1.creatina.service.funcionario;

import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.model.Funcionario;


public interface FuncionarioService {

    Funcionario findById(Long id);

    List<Funcionario> findByNome(String nome);

    Funcionario findByUsername(String username);

    Funcionario findByCpf(String cpf);

    Funcionario findByEmail(String email);

    List<Funcionario> findAll();

    Funcionario create(FuncionarioRequestDTO dto);

    void addTelefone(Long funcionarioId, TelefoneRequestDTO dto);

    void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto);

    void addEndereco(Long funcionarioId, EnderecoRequestDTO dto);

    void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto);

    Funcionario update(Long id, FuncionarioRequestDTO dto);

    void delete(Long id); 
    
}
