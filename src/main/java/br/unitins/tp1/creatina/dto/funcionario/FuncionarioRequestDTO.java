package br.unitins.tp1.creatina.dto.funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FuncionarioRequestDTO(
    // Nome do funcionário (obrigatório)
    @NotBlank(message = "Informe o nome do funcionário.")
    String nome,

    // CPF do funcionário (obrigatório)
    @NotBlank(message = "Informe o CPF do funcionário.")
    String cpf,

    // E-mail do funcionário (obrigatório)
    @NotBlank(message = "Informe o e-mail do funcionário.")
    String email,

    // Cargo do funcionário (obrigatório)
    @NotBlank(message = "Informe o cargo do funcionário.")
    String cargo,

    // Salário do funcionário (obrigatório)
    @NotNull(message = "Informe o salário do funcionário.")
    BigDecimal salario,

    // Data de contratação (obrigatória)
    @NotNull(message = "Informe a data de contratação do funcionário.")
    LocalDate dataContratacao,

    // Data de nascimento (obrigatória)
    @NotNull(message = "Informe a data de nascimento do funcionário.")
    LocalDate dataNascimento,

    // Usuário vinculado ao funcionário (obrigatório)
    @NotNull(message = "Informe o usuário vinculado ao funcionário.")
    UsuarioRequestDTO usuario,

    // Lista de telefones do funcionário
    @NotNull(message = "Informe ao menos um telefone do funcionário.")
    List<TelefoneRequestDTO> telefones,

    // Lista de endereços (pode ser vazio, mas não nulo)
    @NotNull(message = "A lista de endereços não pode ser nula.")
    @Size(min = 1, message = "O funcionario deve ter pelo menos um endereço.")
    List<EnderecoRequestDTO> enderecos
) {}
