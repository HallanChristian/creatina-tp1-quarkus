package br.unitins.tp1.creatina.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum SituacaoPedido{

    ENVIADO(1, "Pedido enviado"),
    ENTREGUE(2, "Pedido entregue"),
    DEVOLVIDO(3, "Devolução do pedido"),
    PAGAMENTO_EM_ESPERA(4, "Aguardando o pagamento do pedido");

    private final Integer id;
    private final String label;
    
    private SituacaoPedido(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static SituacaoPedido valueOf(Integer id) {
        if (id == null)
            return null;
        for (SituacaoPedido tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Situação não encontrada!");
    }

}
