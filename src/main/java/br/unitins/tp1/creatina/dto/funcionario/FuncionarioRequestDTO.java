package br.unitins.tp1.creatina.dto.funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    // Usuário vinculado ao funcionário (obrigatório)
    @NotNull(message = "Informe o usuário vinculado ao funcionário.")
    UsuarioRequestDTO usuario
) {}
