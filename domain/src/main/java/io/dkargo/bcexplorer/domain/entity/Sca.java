package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scas")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Sca {

    @Id
    private String id;

    private String jsonrpc;

    private Result result;

    private String createAt;

    private String updateAt;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        private Integer accType;

        private String balance;

        // 계산 값
        private Double balanceToDouble;

        private Boolean humanReadable;

        private String nonce;

        // 계산 값
        private Long totalTransaction;

        private String type;

        private String codeFormat;

        private String codeHash;

        private String storageRoot;
    }
}