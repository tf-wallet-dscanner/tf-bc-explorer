package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateBlockDTO {

    @ApiModelProperty(value = "블록 번호", required = true)
    @NotNull
    private Long blockNumber;

    @ApiModelProperty(value = "블록 해쉬", required = true)
    @NotNull
    private String blockHash;
}
