package io.dkargo.bcexplorer.dto.domain.kas.block.request;

import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockWithConsensusInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqBlockDTO {

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "resultByGetBlock")
    private ResGetBlockDTO.Result resultByGetBlock;

    @ApiModelProperty(value = "resultByGetBlockWithConsensusInfo")
    private ResGetBlockWithConsensusInfoDTO.Result resultByGetBlockWithConsensusInfo;

    //transaction 에서 계산 된 값.
    @ApiModelProperty(value = "totalTxFee")
    private Double totalTxFee;
}
