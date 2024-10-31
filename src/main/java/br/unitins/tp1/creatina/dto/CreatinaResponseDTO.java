package br.unitins.tp1.creatina.dto;

import br.unitins.tp1.creatina.model.Creatina;

public record CreatinaResponseDTO(
    Long id, 
    String nome, 
    String marca, 
    float quantidadeEmGramas,
    String tipo,
    float preco) {

    public static CreatinaResponseDTO valueOf(Creatina creatina) {
        return new CreatinaResponseDTO (
            creatina.getId(), 
            creatina.getNome(), 
            creatina.getMarca(),
            creatina.getQuantidadeEmGramas(),
            creatina.getTipo(),
            creatina.getPreco());
    }
    
}
