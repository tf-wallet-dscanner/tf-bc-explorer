package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bccommerrors")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BcCommError {

    @Id
    private String id;

    private Long errorId;

    private String jsonrpc;

    private Error error;

    // Caver 메소드 명
    private String caverRequestType;

    // KAS 메소드 명
    private String kasRequestType;

    private String rawResponse;

    private String serviceCode;

    private String createAt;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Error {

        private Integer code;

        private String message;

        private String data;
    }
}
