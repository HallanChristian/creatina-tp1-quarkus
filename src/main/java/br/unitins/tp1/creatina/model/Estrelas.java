package br.unitins.tp1.creatina.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Estrelas {

    UMA_ESTRELA(1, "1 Estrela"),
    DUAS_ESTRELAS(2, "2 Estrelas"),
    TRES_ESTRELAS(3, "3 Estrelas"),
    QUATRO_ESTRELAS(4, "4 Estrelas"),
    CINCO_ESTRELAS(5, "5 Estrelas");

    private final Integer id;
    private final String label;

    private Estrelas(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Estrelas valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (Estrelas tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Estrela não encontrada!");
    }
    
}
