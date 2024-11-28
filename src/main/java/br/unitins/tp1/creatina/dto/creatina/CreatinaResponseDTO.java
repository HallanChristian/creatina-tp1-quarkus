package br.unitins.tp1.creatina.dto.creatina;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.creatina.model.Categoria;
import br.unitins.tp1.creatina.model.Creatina;
import br.unitins.tp1.creatina.model.Fornecedor;

public record CreatinaResponseDTO(
    Long id, 
    String nome, 
    String marca, 
    Integer quantidadeEmGramas,
    String tipo,
    BigDecimal preco,
    Categoria categoria,
    Fornecedor fornecedor,
    List<String> imagens) {

    public static CreatinaResponseDTO valueOf(Creatina creatina) {
        return new CreatinaResponseDTO (
            creatina.getId(), 
            creatina.getNome(), 
            creatina.getMarca(),
            creatina.getQuantidadeEmGramas(),
            creatina.getTipo(),
            creatina.getPreco(),
            creatina.getCategoria(),
            creatina.getFornecedor(),
            creatina.getImagens());
    }
    
}
