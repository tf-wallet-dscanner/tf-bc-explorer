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
public enum CaverRequestType {

    GET_BLOCK_BY_NUMBER("GET_BLOCK_BY_NUMBER", "getBlockByNumber"),
    GET_BLOCK_RECEIPTS("GET_BLOCK_RECEIPTS", "getBlockReceipts"),
    GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER("GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER", "getBlockWithConsensusInfoByNumber");

    private String value;
    private String description;

    @JsonCreator
    public static CaverRequestType enumOf(String value) {
        return Arrays.stream(CaverRequestType.values())
                .filter(t -> t.getValue().equals(value))
                .findAny().orElseThrow(() -> new DkargoException(ErrorCodeEnum.INVALID_FORMAT));
    }

    @JsonValue
    public String getValue() { return value; }

    public static List<Map> getEnumToListMap() {
        List<Map> resultList = new ArrayList<>();
        for(CaverRequestType type : CaverRequestType.values()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("value",type.getValue());
            map.put("description",type.getDescription());
            resultList.add(map);
        }

        return resultList;
    }
}
