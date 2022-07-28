package io.dkargo.bcexplorer.dto.collector.kas.block.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateBlockChainInfoBySchedulerDTO {

    @ApiModelProperty(value = "BC 노드", required = true)
    @NotNull
    private String chain;

    @ApiModelProperty(value = "BC 통신방법", required = true)
    @NotNull
    private String communication;

    @ApiModelProperty(value = "서비스 코드", required = true)
    @NotNull
    private String serviceCode;
}
