package br.unitins.tp1.creatina.dto.funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.creatina.model.Funcionario;

public record FuncionarioResponseDTO (
    Long id,
    String nome,
    String cpf,
    LocalDate dataNascimento,
    String cargo,
    BigDecimal salario,
    LocalDate dataContratacao,
    List<TelefoneResponseDTO> telefones,
    List<EnderecoResponseDTO> enderecos,
    UsuarioResponseDTO usuario
) {

    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
            funcionario.getPessoaFisica().getId(),
            funcionario.getPessoaFisica().getNome(),
            funcionario.getPessoaFisica().getCpf(),
            funcionario.getPessoaFisica().getDataNascimento(),
            funcionario.getCargo(),
            funcionario.getSalario(),
            funcionario.getDataContratacao(),
            funcionario.getPessoaFisica().getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
            funcionario.getPessoaFisica().getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList(),
            UsuarioResponseDTO.valueOf(funcionario.getUsuario())
        );
    }
}

