package io.dkargo.bcexplorer.dto.domain.kas.transaction.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResTransactionDTO {

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "result")
    private Result result;

    @ApiModelProperty(value = "createAt")
    private String createAt;


    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        private String blockNumber;

        private String blockHash;

        private List<TransactionInResult> transactions;

        @Data
        @Builder
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        public static class TransactionInResult {

            private String blockHash;

            private String blockNumber;

            private String codeFormat;

            private String contractAddress;

            private String feePayer;

            private List<FeePayerSignature> feePayerSignatures;

            private String feeRatio;

            private String from;

            private String gas;

            private String gasPrice;

            private String gasPriceToFormat;

            private String gasUsed;

            private String txFee;

            private String key;

            private String input;

            private List<Log> logs;

            private String logsBloom;

            private String nonce;

            private String senderTxHash;

            private List<Signature> signatures;

            private String status;

            private String to;

            private String transactionIndex;

            private String transactionHash;

            private String txError;

            private String type;

            private String typeInt;

            private String value;

            private String amount;

            private String methodSig;

            @Data
            @Builder
            @NoArgsConstructor(access = AccessLevel.PROTECTED)
            @AllArgsConstructor
            public static class FeePayerSignature {

                private String v;

                private String r;

                private String s;
            }

            @Data
            @Builder
            @NoArgsConstructor(access = AccessLevel.PROTECTED)
            @AllArgsConstructor
            public static class Log {

                private BigInteger logIndex;

                private BigInteger transactionIndex;

                private String transactionHash;

                private String blockHash;

                private BigInteger blockNumber;

                private String address;

                private String data;

                private List<String> topics;

                private String logIndexRaw;

                private String transactionIndexRaw;

                private String blockNumberRaw;
            }

            @Data
            @Builder
            @NoArgsConstructor(access = AccessLevel.PROTECTED)
            @AllArgsConstructor
            public static class Signature {

                private String v;

                private String r;

                private String s;
            }
        }
    }
}
