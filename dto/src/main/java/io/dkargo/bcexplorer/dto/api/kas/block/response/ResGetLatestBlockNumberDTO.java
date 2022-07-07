package io.dkargo.bcexplorer.dto.api.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetLatestBlockNumberDTO {

    @ApiModelProperty(value = "Block Number")
    private Long blockNumber;
}
