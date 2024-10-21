package br.unitins.tp1.creatina.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.creatina.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    LocalDate dataNascimento,
    String email, 
    List<TelefoneClienteResponseDTO> telefones,
    List<EnderecoResponseDTO> enderecos) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO (
            cliente.getId(),
            cliente.getNome(), 
            cliente.getCpf(),
            cliente.getDataNascimento(),
            cliente.getEmail(),
            cliente.getTelefones().stream().map(TelefoneClienteResponseDTO::valueOf).collect(Collectors.toList()),
            cliente.getEnderecos().stream().map(EnderecoResponseDTO::valueOf).collect(Collectors.toList())
            );
    }
    
}
