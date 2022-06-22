package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetBlockWithConsensusInfoDTO {

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

        @ApiModelProperty(value = "blockScore")
        private String blockScore;

        @ApiModelProperty(value = "totalBlockScore")
        private String totalBlockScore;

        @ApiModelProperty(value = "committee")
        private List<String> committee;

        @ApiModelProperty(value = "gasLimit")
        private String gasLimit;

        @ApiModelProperty(value = "gasUsed")
        private String gasUsed;

        @ApiModelProperty(value = "hash")
        @NotNull
        private String hash;

        @ApiModelProperty(value = "miner")
        private String miner;

        @ApiModelProperty(value = "nonce")
        private String nonce;

        @ApiModelProperty(value = "number")
        @NotNull
        private String number;

        @ApiModelProperty(value = "parentBlockHash")
        private String parentBlockHash;

        @ApiModelProperty(value = "proposer")
        private String proposer;

        @ApiModelProperty(value = "receiptsRoot")
        private String receiptsRoot;

        @ApiModelProperty(value = "size")
        private String size;

        @ApiModelProperty(value = "stateRoot")
        private String stateRoot;

        @ApiModelProperty(value = "timestamp")
        private String timestamp;

        @ApiModelProperty(value = "timestampFoS")
        private String timestampFoS;

        @ApiModelProperty(value = "transactions")
        private List<Transactions> transactions;

        @ApiModelProperty(value = "transactionsRoot")
        private String transactionsRoot;


        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class Transactions {

            @ApiModelProperty(value = "blockHash")
            private String blockHash;

            @ApiModelProperty(value = "blockNumber")
            private String blockNumber;

            @ApiModelProperty(value = "codeFormat")
            private String codeFormat;

            @ApiModelProperty(value = "feePayer")
            private String feePayer;

            @ApiModelProperty(value = "feePayerSignatures")
            private List<FeePayerSignatures> feePayerSignatures;

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
            private List<Signatures> signatures;

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
            public static class FeePayerSignatures {

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
            public static class Signatures {

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
