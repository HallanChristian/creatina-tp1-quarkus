package br.unitins.tp1.creatina.dto.cliente;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.unitins.tp1.creatina.dto.EnderecoRequestDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
    @Email String email,

    List<TelefoneRequestDTO> telefones,
    List<EnderecoRequestDTO> enderecos,
    UsuarioRequestDTO usuario
) {}
