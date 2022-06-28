package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetBlockDTO {

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

        @ApiModelProperty(value = "number")
        private String number;

        @ApiModelProperty(value = "hash")
        private String hash;

        @ApiModelProperty(value = "parentHash")
        private String parentHash;

        @ApiModelProperty(value = "logsBloom")
        private String logsBloom;

        @ApiModelProperty(value = "transactionsRoot")
        private String transactionsRoot;

        @ApiModelProperty(value = "stateRoot")
        private String stateRoot;

        @ApiModelProperty(value = "receiptsRoot")
        private String receiptsRoot;

        @ApiModelProperty(value = "reward")
        private String reward;

        @ApiModelProperty(value = "blockScore")
        private String blockScore;

        @ApiModelProperty(value = "totalBlockScore")
        private String totalBlockScore;

        @ApiModelProperty(value = "extraData")
        private String extraData;

        @ApiModelProperty(value = "size")
        private String size;

        @ApiModelProperty(value = "gasUsed")
        private String gasUsed;

        @ApiModelProperty(value = "timestamp")
        private String timestamp;

        @ApiModelProperty(value = "timestampFoS")
        private String timestampFoS;

        @ApiModelProperty(value = "governanceData")
        private String governanceData;

        @ApiModelProperty(value = "voteData")
        private String voteData;

        @ApiModelProperty(value = "transactions")
        private List<Transaction> transactions;

        @ApiModelProperty(value = "transactionCount")
        private Integer transactionCount;


        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Transaction {

            @ApiModelProperty(value = "blockHash")
            private String blockHash;

            @ApiModelProperty(value = "blockNumber")
            private String blockNumber;

            @ApiModelProperty(value = "codeFormat")
            private String codeFormat;

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

            @ApiModelProperty(value = "hash")
            private String hash;

            // X
            // @ApiModelProperty(value = "humanReadable")
            // private String humanReadable;

            @ApiModelProperty(value = "key")
            private String key;

            @ApiModelProperty(value = "input")
            private String input;

            @ApiModelProperty(value = "nonce")
            private String nonce;

            @ApiModelProperty(value = "senderTxHash")
            private String senderTxHash;

            @ApiModelProperty(value = "signatures")
            private List<Signature> signatures;

            @ApiModelProperty(value = "to")
            private String to;

            @ApiModelProperty(value = "transactionIndex")
            private String transactionIndex;

            @ApiModelProperty(value = "type")
            private String type;

            @ApiModelProperty(value = "typeInt")
            private String typeInt;

            @ApiModelProperty(value = "value")
            private String value;

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
            public static class Signature {

                @ApiModelProperty(value = "v")
                private String v;

                @ApiModelProperty(value = "r")
                private String r;

                @ApiModelProperty(value = "s")
                private String s;
            }
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
