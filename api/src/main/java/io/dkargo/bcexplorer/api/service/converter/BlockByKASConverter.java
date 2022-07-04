package io.dkargo.bcexplorer.api.service.converter;

import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.dto.domain.kas.block.response.ResBlockDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockByKASConverter {

    // block -> res
    public static ResBlockDTO of(Block block) {

        ResBlockDTO.Result result = ResBlockDTO.Result.builder()
                .number(block.getResult().getNumber())
                .hash(block.getResult().getHash())
                .parentHash(block.getResult().getParentHash())
                .logsBloom(block.getResult().getLogsBloom())
                .transactionsRoot(block.getResult().getTransactionsRoot())
                .stateRoot(block.getResult().getStateRoot())
                .receiptsRoot(block.getResult().getReceiptsRoot())
                .reward(block.getResult().getReward())
                .blockScore(block.getResult().getBlockScore())
                .totalBlockScore(block.getResult().getTotalBlockScore())
                .extraData(block.getResult().getExtraData())
                .size(block.getResult().getSize())
                .gasUsed(block.getResult().getGasUsed())
                .gasLimit(block.getResult().getGasLimit())
                .timestamp(block.getResult().getTimestamp())
                .timestampFoS(block.getResult().getTimestampFoS())
                .governanceData(block.getResult().getGovernanceData())
                .voteData(block.getResult().getVoteData())
                .proposer(block.getResult().getProposer())
                .committee(block.getResult().getCommittee())
                .transactionCount(block.getResult().getTransactionCount())
                .build();

        return ResBlockDTO.builder()
                .id(block.getId())
                .jsonrpc(block.getJsonrpc())
                .result(result)
                .createAt(block.getCreateAt())
                .build();
    }
}
