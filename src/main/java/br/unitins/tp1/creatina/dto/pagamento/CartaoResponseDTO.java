package br.unitins.tp1.creatina.dto.pagamento;

import java.time.LocalDate;

import br.unitins.tp1.creatina.model.Cartao;
import br.unitins.tp1.creatina.model.TipoCartao;

public record CartaoResponseDTO(
    Long id,
    String numero,
    String nomeTitular,
    LocalDate validade,
    String cvc,
    TipoCartao tipoCartao
) {

    public static CartaoResponseDTO valueOf(Cartao cartao) {
        return new CartaoResponseDTO(
            cartao.getId(),
            cartao.getNumero(),
            cartao.getNomeTitular(),
            cartao.getValidade(),
            cartao.getCvc(),
            cartao.getTipoCartao()
        );
    }
}

