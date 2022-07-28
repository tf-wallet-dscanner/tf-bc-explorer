package io.dkargo.bcexplorer.core.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.dkargo.bcexplorer.core.error.DkargoException;
import io.dkargo.bcexplorer.core.error.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum CommunicationType {

    CAVER("CAVER", "caver"),
    KAS("KAS", "kas");

    private String value;
    private String desciption;

    @JsonCreator
    public static CommunicationType enumOf(String value) {
        return Arrays.stream(CommunicationType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElseThrow(() -> new DkargoException(ErrorCodeEnum.INVALID_FORMAT));
    }

    @JsonValue
    public String getValue() { return value; }

    public static List<Map> getEnumToListMap() {
        List<Map> resultList = new ArrayList<>();
        for(CommunicationType type : CommunicationType.values()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("value", type.getValue());
            map.put("description", type.getDesciption());
            resultList.add(map);
        }

        return resultList;
    }
}
