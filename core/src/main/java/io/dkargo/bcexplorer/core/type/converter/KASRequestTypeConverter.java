package io.dkargo.bcexplorer.core.type.converter;

import io.dkargo.bcexplorer.core.type.KASRequestType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class KASRequestTypeConverter implements AttributeConverter<KASRequestType, String> {

    @Override
    public String convertToDatabaseColumn(KASRequestType kasRequestType) {
        if (kasRequestType == null) return null;
        return kasRequestType.getValue();
    }

    @Override
    public KASRequestType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(KASRequestType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

}
