package io.dkargo.bcexplorer.dto.collector.kas.block.request;

import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockWithConsensusInfoDTO;
import io.dkargo.bcexplorer.dto.collector.kas.transaction.response.ResGetTransactionReceiptByHashDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqBlockDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "error")
    private ResGetBlockWithConsensusInfoDTO.Error error;

    @ApiModelProperty(value = "rawResponse")
    private String rawResponse;

    @ApiModelProperty(value = "blockScore")
    private String blockScore;

    @ApiModelProperty(value = "totalBlockScore")
    private String totalBlockScore;

    @ApiModelProperty(value = "committee")
    private List<String> committee;

    @ApiModelProperty(value = "gasLimit")
    private String gasLimit;

    @ApiModelProperty(value = "gasUsed")
    private String gasUsed;

    @ApiModelProperty(value = "hash")
    @NotNull
    private String hash;

    @ApiModelProperty(value = "miner")
    private String miner;

    @ApiModelProperty(value = "nonce")
    private String nonce;

    @ApiModelProperty(value = "number")
    @NotNull
    private String number;

    @ApiModelProperty(value = "parentBlockHash")
    private String parentBlockHash;

    @ApiModelProperty(value = "proposer")
    private String proposer;

    @ApiModelProperty(value = "receiptsRoot")
    private String receiptsRoot;

    @ApiModelProperty(value = "size")
    private String size;

    @ApiModelProperty(value = "stateRoot")
    private String stateRoot;

    @ApiModelProperty(value = "timestamp")
    private String timestamp;

    @ApiModelProperty(value = "timestampFoS")
    private String timestampFoS;

    @ApiModelProperty(value = "transactionsRoot")
    private String transactionsRoot;

    @ApiModelProperty(value = "transactionCount")
    private Long transactionCount;

    @ApiModelProperty(value = "transactions")
    private List<ResGetTransactionReceiptByHashDTO> transactions;
}
