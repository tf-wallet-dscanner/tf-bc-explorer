package io.dkargo.bcexplorer.dto.domain.kas.transaction.request;

import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockReceiptDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqTransactionDTO {

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "results")
    private ResGetBlockReceiptDTO.Result result;

    @ApiModelProperty(value = "blockNumber")
    private String blockNumber;

    @ApiModelProperty(value = "blockHash")
    private String blockHash;
}
