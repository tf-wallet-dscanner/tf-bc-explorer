package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetLatestBlockNumberDTO {

    @ApiModelProperty(value = "블록 번호")
    @NotNull
    private Long blockNumber;
}
