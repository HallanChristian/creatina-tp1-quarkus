package br.unitins.tp1.creatina.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum SituacaoPagamento {

    PENDENTE(1, "Pendente"), 
    PAGO(2, "Pago"), 
    CANCELADO(3, "Cancelado"),
    VENCIDO(4, "Vencido");
    
    private final Integer id;
    private final String label;

    private SituacaoPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static SituacaoPagamento valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (SituacaoPagamento tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Situação não encontrada!");
    }
    
}
