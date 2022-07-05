package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "blocks")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Block {

    @Id
    private String id;

    private String jsonrpc;

    private Result result;

    private String createAt;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        private String number;

        private String hash;

        private String parentHash;

        private String logsBloom;

        private String transactionsRoot;

        private String stateRoot;

        private String receiptsRoot;

        private String reward;

        private String blockReward;

        private Double rewardToDouble;

        private String blockScore;

        private String totalBlockScore;

        private String extraData;

        private String size;

        private String gasUsed;

        private String gasLimit;

        private String timestamp;

        private String timestampFoS;

        private String governanceData;

        private String voteData;

        private String proposer;

        private List<String> committee;

        private Integer transactionCount;
    }
}
