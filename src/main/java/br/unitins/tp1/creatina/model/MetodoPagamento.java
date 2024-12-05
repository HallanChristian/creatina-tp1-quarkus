package br.unitins.tp1.creatina.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum MetodoPagamento {

    PIX(1, "Pix"), CARTAO(2, "Cartão"), BOLETO(3, "Boleto"), NAO_EFETUADO(4, "Não efetuado");

    private final Integer id;
    private final String label;

    private MetodoPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static MetodoPagamento valueOf(Integer id) {
        if (id == null)
            return null;
        for (MetodoPagamento tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Metodo de pagameto não encontrado!");
    }   

    
}
