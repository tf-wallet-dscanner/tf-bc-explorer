package io.dkargo.bcexplorer.dto.domain.kas.account.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResScaDTO {

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "result")
    private Result result;

    @ApiModelProperty(value = "createAt")
    private String createAt;

    @ApiModelProperty(value = "createAt")
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
