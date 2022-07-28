package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "transactions")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Transaction {

    @Id
    private String id;

    private String jsonrpc;

    private Result result;

    private String serviceCode;

    private String createAt;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

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
