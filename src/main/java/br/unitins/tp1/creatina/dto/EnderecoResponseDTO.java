package br.unitins.tp1.creatina.dto;

import br.unitins.tp1.creatina.model.Endereco;

public record EnderecoResponseDTO(
    Long id, 
    String cep,
    String logradouro, 
    String numero,
    String cidade,
    String estado) {

    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO (
            endereco.getId(),
            endereco.getCep(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getCidade(),
            endereco.getEstado());
    }
    
}
