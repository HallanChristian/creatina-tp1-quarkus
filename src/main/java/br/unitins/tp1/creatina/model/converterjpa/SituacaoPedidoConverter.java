package br.unitins.tp1.creatina.model.converterjpa;

import br.unitins.tp1.creatina.model.SituacaoPedido;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoPedidoConverter implements AttributeConverter<SituacaoPedido,Integer> {

    @Override
    public Integer convertToDatabaseColumn(SituacaoPedido situacaopedido) {
        return situacaopedido == null ? null : situacaopedido.getId();
    }

    @Override
    public SituacaoPedido convertToEntityAttribute(Integer id) {
       return SituacaoPedido.valueOf(id);
    }

}
