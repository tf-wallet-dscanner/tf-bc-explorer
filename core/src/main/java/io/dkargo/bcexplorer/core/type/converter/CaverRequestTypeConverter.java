package io.dkargo.bcexplorer.core.type.converter;

import io.dkargo.bcexplorer.core.type.CaverRequestType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class CaverRequestTypeConverter implements AttributeConverter<CaverRequestType, String> {

    @Override
    public String convertToDatabaseColumn(CaverRequestType caverRequestType) {
        if (caverRequestType == null) return null;
        return caverRequestType.getValue();
    }

    @Override
    public CaverRequestType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(CaverRequestType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
