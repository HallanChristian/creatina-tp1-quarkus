package br.unitins.tp1.faixas.dto;

import br.unitins.tp1.faixas.model.Endereco;

public record EnderecoResponseDTO(
    Long id, 
    String cep,
    String logradouro, 
    String numero,
    MunicipioResponseDTO municipio) {

    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO (
            endereco.getId(),
            endereco.getCep(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            MunicipioResponseDTO.valueOf(endereco.getMunicipio()));
    }
    
}
