package io.dkargo.bcexplorer.core.type.converter;

import io.dkargo.bcexplorer.core.type.CommunicationType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

public class CommunicationTypeConverter implements AttributeConverter<CommunicationType, String> {

    @Override
    public String convertToDatabaseColumn(CommunicationType communicationType) {
        if (communicationType == null) return null;
        return communicationType.getValue();
    }

    @Override
    public CommunicationType convertToEntityAttribute(String value) {
        if (value == null) return null;

        return Stream.of(CommunicationType.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
