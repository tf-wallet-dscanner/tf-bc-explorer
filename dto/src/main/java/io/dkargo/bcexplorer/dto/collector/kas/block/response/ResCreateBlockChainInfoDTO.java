package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateBlockChainInfoDTO {

    @ApiModelProperty(value = "블록 번호", required = true)
    @NotNull
    private Long blockNumber;

    @ApiModelProperty(value = "블록 해시", required = true)
    @NotNull
    private String blockHash;

    @ApiModelProperty(value = "트랜잭션 해시 리스트")
    private List<String> transactionHashList;
}
