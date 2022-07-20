package io.dkargo.bcexplorer.dto.collector.kas.block.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateBlockChainInfoByNumberDTO {

    @ApiModelProperty(value = "블록 번호", required = true)
    @NotNull
    private Long blockNumber;
}
