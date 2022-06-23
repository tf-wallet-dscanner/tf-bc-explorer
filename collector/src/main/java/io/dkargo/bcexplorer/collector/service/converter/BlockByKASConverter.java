package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockWithConsensusInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

@Slf4j
public class BlockByKASConverter {

    // req -> block
    public static Block of(ReqBlockDTO reqBlockDTO) {

        log.info("cute hoony : {}", reqBlockDTO.getTransactions().size());
        log.info("hoony jjang : {}", reqBlockDTO.getTransactions());
        log.info("good hoony : {}", CommonConverter.objectToJsonString(reqBlockDTO.getTransactions()));
        log.info("baboo hoony : {}", CommonConverter.objectToString(reqBlockDTO.getTransactions()));

        return Block.builder()
                .blockId(reqBlockDTO.getId())
                .jsonrpc(reqBlockDTO.getJsonrpc())
                .error(CommonConverter.objectToString(reqBlockDTO.getError())) // string 으로 변환 후 build
                .rawResponse(reqBlockDTO.getRawResponse())
                .blockScore(reqBlockDTO.getBlockScore())
                .totalBlockScore(reqBlockDTO.getTotalBlockScore())
                .committee(reqBlockDTO.getCommittee())
                .gasLimit(reqBlockDTO.getGasLimit())
                .gasUsed(reqBlockDTO.getGasUsed())
                .hash(reqBlockDTO.getHash())
                .miner(reqBlockDTO.getMiner())
                .nonce(reqBlockDTO.getNonce())
                .number(reqBlockDTO.getNumber())
                .parentBlockHash(reqBlockDTO.getParentBlockHash())
                .proposer(reqBlockDTO.getProposer())
                .receiptsRoot(reqBlockDTO.getReceiptsRoot())
                .size(reqBlockDTO.getSize())
                .stateRoot(reqBlockDTO.getStateRoot())
                .timestamp(reqBlockDTO.getTimestamp())
                .timestampFoS(reqBlockDTO.getTimestampFoS())
                .transactionsRoot(reqBlockDTO.getTransactionsRoot())
                .transactionCount(reqBlockDTO.getTransactionCount())
                .transactions(CommonConverter.objectToString(reqBlockDTO.getTransactions()))
                .createAt(CommonConverter.currentDateTime())
                .build();
    }

    // block -> res
    public static ResBlockDTO of(Block block) {

        JSONObject jsonObj = CommonConverter.stringToObject(block.getError());
        ResGetBlockWithConsensusInfoDTO.Error error = ResGetBlockWithConsensusInfoDTO.Error.builder()
                .code((Integer) jsonObj.get("code"))
                .message((String) jsonObj.get("message"))
                .data((String) jsonObj.get("data"))
                .build();

        return ResBlockDTO.builder()
                .id(block.getId())
                .blockId(block.getBlockId())
                .jsonrpc(block.getJsonrpc())
                .error(error) // string을 객체형태로 변환 후 build
                .rawResponse(block.getRawResponse())
                .blockScore(block.getBlockScore())
                .totalBlockScore(block.getTotalBlockScore())
                .committee(block.getCommittee())
                .gasLimit(block.getGasLimit())
                .gasUsed(block.getGasUsed())
                .hash(block.getHash())
                .miner(block.getMiner())
                .nonce(block.getNonce())
                .number(block.getNumber())
                .parentBlockHash(block.getParentBlockHash())
                .proposer(block.getProposer())
                .receiptsRoot(block.getReceiptsRoot())
                .size(block.getSize())
                .stateRoot(block.getStateRoot())
                .timestamp(block.getTimestamp())
                .timestampFoS(block.getTimestampFoS())
                .transactionsRoot(block.getTransactionsRoot())
                .transactionCount(block.getTransactionCount())
                .createAt(block.getCreateAt())
                .build();
    }
}
