package br.unitins.tp1.faixas.dto;

import br.unitins.tp1.faixas.model.Endereco;

public record EnderecoResponseDTO(
    Long id, 
    String cep,
    String cidade,
    String estado,
    String logradouro, 
    String numero,
    Long idCliente) {

    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO (
            endereco.getId(),
            endereco.getCep(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getIdCliente());
    }
    
}
