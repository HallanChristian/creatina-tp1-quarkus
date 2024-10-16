package br.unitins.tp1.faixas.dto;

import br.unitins.tp1.faixas.model.Creatina;

public record CreatinaResponseDTO(
    Long id, 
    String nome, 
    String marca, 
    double quantidadeEmGramas,
    String tipo,
    double preco) {

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
