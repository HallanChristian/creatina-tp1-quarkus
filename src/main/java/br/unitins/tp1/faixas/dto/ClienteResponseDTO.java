package br.unitins.tp1.faixas.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.faixas.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String  cpf,
    LocalDate dataNascimento,
    String email, 
    List<EnderecoResponseDTO> enderecos) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO (
            cliente.getId(),
            cliente.getNome(), 
            cliente.getCpf(),
            cliente.getDataNascimento(),
            cliente.getEmail(),
            cliente.getEnderecos().stream()
            .map(EnderecoResponseDTO::valueOf)
            .collect(Collectors.toList())
            );
    }
    
}
