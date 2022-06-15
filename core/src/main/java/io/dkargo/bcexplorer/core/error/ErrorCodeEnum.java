package io.dkargo.bcexplorer.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    BAD_REQUEST(400, 10000, "Bad Request (잘못된 요청입니다.)"),
    INVALID_FORMAT(400, 10001, "Invalid Data Format (입력 데이터를 확인하십시오.)"),
    UNAUTHORIZED_ERROR(401, 10002, "Unauthorized (권한이 없습니다.)"),
    INTERNAL_SERVER_ERROR(500, 10003, "Internal Server Error (서버에서 처리중 에러가 발생하였습니다.)"),
    JSON_PARSING_ERROR(500, 10004, "Json Data Parsing Error (데이터 파싱중 에러가 발생하였습니다.)"),
    CONNECTION_REFUSED_ERROR(500, 10005, "Connection Refused (서버와 연결을 확인하십시오.)"),
    RESOURCE_ACCESS_ERROR(500, 10006, "Network I/O Error (서버와 연결 방식을 확인하십시오.)"),
    DATA_NOT_FOUND(404, 10007, "Data Not Found (데이터가 존재하지 않습니다.)"),
    HTTPCLIENT_ERROR(500, 10008, "HTTP CLIENT Error");

    private int status;
    private int code;
    private String message;

    public static ErrorCodeEnum enumOf(int code) {
        return Arrays.stream(ErrorCodeEnum.values())
                .filter(t -> t.getCode() == code)
                .findAny().orElse(null);
    }
}
