package io.dkargo.bcexplorer.dto.domain.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResBlockDTO {

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "result")
    private Result result;

    @ApiModelProperty(value = "serviceCode")
    private String serviceCode;

    @ApiModelProperty(value = "createAt")
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
