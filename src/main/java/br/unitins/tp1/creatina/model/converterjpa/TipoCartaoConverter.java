package br.unitins.tp1.creatina.model.converterjpa;

import br.unitins.tp1.creatina.model.TipoCartao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoCartaoConverter implements AttributeConverter<TipoCartao,Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoCartao tipoCartao) {
        return tipoCartao == null ? null : tipoCartao.getId();
    }

    @Override
    public TipoCartao convertToEntityAttribute(Integer id) {
       return TipoCartao.valueOf(id);
    }

}
