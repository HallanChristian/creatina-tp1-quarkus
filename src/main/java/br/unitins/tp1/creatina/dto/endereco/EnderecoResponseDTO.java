package br.unitins.tp1.creatina.dto.endereco;

import br.unitins.tp1.creatina.dto.municipio.MunicipioResponseDTO;
import br.unitins.tp1.creatina.model.Endereco;

public record EnderecoResponseDTO(
    Long id, 
    String cep,
    String logradouro, 
    String numero,
    String bairro,
    String complemento,
    MunicipioResponseDTO municipio) {

    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO (
            endereco.getId(),
            endereco.getCep(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getComplemento(),
            endereco.getMunicipio() != null ? MunicipioResponseDTO.valueOf(endereco.getMunicipio()) : null);
    }
    
}
