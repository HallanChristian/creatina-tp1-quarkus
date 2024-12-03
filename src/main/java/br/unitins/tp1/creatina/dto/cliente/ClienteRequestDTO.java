package br.unitins.tp1.creatina.dto.cliente;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.unitins.tp1.creatina.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

// DTO Cliente

public record ClienteRequestDTO(
    // Nome do cliente (obrigatório)
    @NotBlank(message = "Informe o nome do cliente.")
    String nome, 

    // CPF do cliente (obrigatório)
    @NotBlank(message = "Informe o CPF do cliente.") 
    @CPF(message = "Formato do CPF informado é inválido")
    String cpf,

    // E-mail do cliente (obrigatório)
    @NotBlank(message = "Informe o e-mail do cliente.")
    @Email(message = "Formato de e-mail inválido.")
    String email,
    
    // Data de nascimento (obrigatória)
    @NotNull(message = "Informe a data de nascimento do cliente.")
    @Past(message = "A data de nascimento deve ser no passado.")
    LocalDate dataNascimento,

    // Lista de telefones (pode ser vazio, mas não nulo)
    @NotNull(message = "A lista de telefones não pode ser nula.")
    @Size(min = 1, message = "O cliente deve ter pelo menos um telefone.")
    List<TelefoneRequestDTO> telefones,

    // Lista de endereços (pode ser vazio, mas não nulo)
    @NotNull(message = "A lista de endereços não pode ser nula.")
    @Size(min = 1, message = "O cliente deve ter pelo menos um endereço.")
    List<EnderecoRequestDTO> enderecos,

    // Dados de usuário
    UsuarioRequestDTO usuario
) {}
