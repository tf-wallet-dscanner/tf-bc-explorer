package io.dkargo.bcexplorer.dto.collector.kas.transaction.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetTransactionReceiptByHashDTO {

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

        @ApiModelProperty(value = "blockHash")
        private String blockHash;

        @ApiModelProperty(value = "blockNumber")
        private String blockNumber;

        @ApiModelProperty(value = "codeFormat")
        private String codeFormat;

        @ApiModelProperty(value = "contractAddress")
        private String contractAddress;

        @ApiModelProperty(value = "feePayer")
        private String feePayer;

        @ApiModelProperty(value = "feePayerSignatures")
        private List<FeePayerSignature> feePayerSignatures;

        @ApiModelProperty(value = "feeRatio")
        private String feeRatio;

        @ApiModelProperty(value = "from")
        private String from;

        @ApiModelProperty(value = "gas")
        private String gas;

        @ApiModelProperty(value = "gasPrice")
        private String gasPrice;

        @ApiModelProperty(value = "gasPriceByFormat")
        private String gasPriceByFormat;

        @ApiModelProperty(value = "gasUsed")
        private String gasUsed;

        @ApiModelProperty(value = "txFee")
        private String txFee;

        // X
        // @ApiModelProperty(value = "humanReadable")
        // private String humanReadable;

        @ApiModelProperty(value = "key")
        private String key;

        @ApiModelProperty(value = "input")
        private String input;

        @ApiModelProperty(value = "logs")
        private List<Log> logs;

        @ApiModelProperty(value = "logsBloom")
        private String logsBloom;

        @ApiModelProperty(value = "nonce")
        private String nonce;

        @ApiModelProperty(value = "senderTxHash")
        private String senderTxHash;

        @ApiModelProperty(value = "signatures")
        private List<Signature> signatures;

        @ApiModelProperty(value = "status")
        private String status;

        @ApiModelProperty(value = "to")
        private String to;

        @ApiModelProperty(value = "transactionIndex")
        private String transactionIndex;

        @ApiModelProperty(value = "txError")
        private String txError;

        @ApiModelProperty(value = "type")
        private String type;

        @ApiModelProperty(value = "typeInt")
        private String typeInt;

        @ApiModelProperty(value = "value")
        private String value;

        @ApiModelProperty(value = "amount")
        private String amount;

        @ApiModelProperty(value = "methodSig")
        private String methodSig;

        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class FeePayerSignature {

            @ApiModelProperty(value = "v")
            private String v;

            @ApiModelProperty(value = "r")
            private String r;

            @ApiModelProperty(value = "s")
            private String s;
        }

        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Log {

            @ApiModelProperty(value = "logIndex")
            private BigInteger logIndex;

            @ApiModelProperty(value = "transactionIndex")
            private BigInteger transactionIndex;

            @ApiModelProperty(value = "transactionHash")
            private String transactionHash;

            @ApiModelProperty(value = "blockHash")
            private String blockHash;

            @ApiModelProperty(value = "blockNumber")
            private BigInteger blockNumber;

            @ApiModelProperty(value = "address")
            private String address;

            @ApiModelProperty(value = "data")
            private String data;

            @ApiModelProperty(value = "topics")
            private List<String> topics;

            @ApiModelProperty(value = "logIndexRaw")
            private String logIndexRaw;

            @ApiModelProperty(value = "transactionIndexRaw")
            private String transactionIndexRaw;

            @ApiModelProperty(value = "blockNumberRaw")
            private String blockNumberRaw;
        }

        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Signature {

            @ApiModelProperty(value = "v")
            private String v;

            @ApiModelProperty(value = "r")
            private String r;

            @ApiModelProperty(value = "s")
            private String s;
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
