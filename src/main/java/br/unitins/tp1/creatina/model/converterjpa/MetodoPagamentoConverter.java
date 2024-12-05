package br.unitins.tp1.creatina.model.converterjpa;

import br.unitins.tp1.creatina.model.MetodoPagamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MetodoPagamentoConverter implements AttributeConverter<MetodoPagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MetodoPagamento metodoPagamento) {
        return metodoPagamento == null ? null : metodoPagamento.getId();
    }

    @Override
    public MetodoPagamento convertToEntityAttribute(Integer id) {
        return MetodoPagamento.valueOf(id);
    }

}