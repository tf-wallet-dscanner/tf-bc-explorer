package io.dkargo.bcexplorer.dto.collector.kas.account.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetAccountDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "result")
    private Result result;

    @ApiModelProperty(value = "error")
    private Error error;

    @ApiModelProperty(value = "rawResponse")
    private String rawResponse;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        @ApiModelProperty(value = "accType")
        private Integer accType;

        @ApiModelProperty(value = "account")
        private Account account;

        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Account {

            @ApiModelProperty(value = "address")
            private String address;

            @ApiModelProperty(value = "balance")
            private String balance;

            @ApiModelProperty(value = "humanReadable")
            private Boolean humanReadable;

            // maybe.. SCA 에만 존재
            @ApiModelProperty(value = "codeFormat")
            private String codeFormat;

            // maybe.. SCA 에만 존재
            @ApiModelProperty(value = "codeHash")
            private String codeHash;

            // TODO : Key 안에 있는 값은 분석 후 추가!!
//            @Data
//            @Builder
//            @NoArgsConstructor(access = AccessLevel.PROTECTED)
//            @AllArgsConstructor
//            public static class Key {
//            }

            @ApiModelProperty(value = "nonce")
            private String nonce;

            @ApiModelProperty(value = "type")
            private String type;

            // maybe.. SCA 에만 존재
            @ApiModelProperty(value = "storageRoot")
            private String storageRoot;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Error {

        @ApiModelProperty(value = "code")
        private Integer code;

        @ApiModelProperty(value = "message")
        private String message;

        @ApiModelProperty(value = "data")
        private String data;
    }
}
