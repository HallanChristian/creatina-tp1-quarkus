package br.unitins.tp1.faixas.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.faixas.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String  cpf,
    LocalDate dataNascimento,
    String email, 
    List<TelefoneResponseDTO> telefones,
    List<EnderecoResponseDTO> enderecos) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO (
            cliente.getId(),
            cliente.getNome(), 
            cliente.getCpf(),
            cliente.getDataNascimento(),
            cliente.getEmail(),
            cliente.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
            cliente.getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList()
            );
    }
    
}
