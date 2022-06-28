package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.dto.domain.kas.block.request.ReqBlockDTO;
import io.dkargo.bcexplorer.dto.domain.kas.block.response.ResBlockDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockByKASConverter {

    // req -> block
    public static Block of(ReqBlockDTO reqBlockDTO) {

        Block.Result result = Block.Result.builder()
                .number(reqBlockDTO.getResultByGetBlock().getNumber())
                .hash(reqBlockDTO.getResultByGetBlock().getHash())
                .parentHash(reqBlockDTO.getResultByGetBlock().getParentHash())
                .logsBloom(reqBlockDTO.getResultByGetBlock().getLogsBloom())
                .transactionsRoot(reqBlockDTO.getResultByGetBlock().getTransactionsRoot())
                .stateRoot(reqBlockDTO.getResultByGetBlock().getStateRoot())
                .receiptsRoot(reqBlockDTO.getResultByGetBlock().getReceiptsRoot())
                .reward(reqBlockDTO.getResultByGetBlock().getReward())
                .blockScore(reqBlockDTO.getResultByGetBlock().getBlockScore())
                .totalBlockScore(reqBlockDTO.getResultByGetBlock().getTotalBlockScore())
                .extraData(reqBlockDTO.getResultByGetBlock().getExtraData())
                .size(reqBlockDTO.getResultByGetBlock().getSize())
                .gasUsed(reqBlockDTO.getResultByGetBlock().getGasUsed())
                .gasLimit(reqBlockDTO.getResultByGetBlockWithConsensusInfo().getGasLimit())
                .timestamp(reqBlockDTO.getResultByGetBlock().getTimestamp())
                .timestampFoS(reqBlockDTO.getResultByGetBlock().getTimestampFoS())
                .governanceData(reqBlockDTO.getResultByGetBlock().getGovernanceData())
                .voteData(reqBlockDTO.getResultByGetBlock().getVoteData())
                .proposer(reqBlockDTO.getResultByGetBlockWithConsensusInfo().getProposer())
                .committee(reqBlockDTO.getResultByGetBlockWithConsensusInfo().getCommittee())
                .transactionCount(reqBlockDTO.getResultByGetBlock().getTransactionCount())
                .build();

        return Block.builder()
                .jsonrpc(reqBlockDTO.getJsonrpc())
                .result(result)
                .createAt(CommonConverter.currentDateTime())
                .build();
    }

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
