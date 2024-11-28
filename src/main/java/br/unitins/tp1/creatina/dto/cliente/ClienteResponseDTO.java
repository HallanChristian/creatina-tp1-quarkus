package br.unitins.tp1.creatina.dto.cliente;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.creatina.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.creatina.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.creatina.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.creatina.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    LocalDate dataNascimento,
    String email, 
    List<TelefoneResponseDTO> telefones,
    List<EnderecoResponseDTO> enderecos,
    UsuarioResponseDTO usuario) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO (
            cliente.getId(),
            cliente.getNome(), 
            cliente.getCpf(),
            cliente.getDataNascimento(),
            cliente.getEmail(),
            cliente.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
            cliente.getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList(),
            UsuarioResponseDTO.valueOf(cliente.getUsuario())
            );
    }
    
}
