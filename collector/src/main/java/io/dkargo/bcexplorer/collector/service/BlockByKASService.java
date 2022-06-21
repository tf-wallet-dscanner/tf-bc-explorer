package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;

public interface BlockByKASService {

    ResGetLatestBlockNumberDTO getLatestBlockNumber();

    ResGetBlockDTO getBlockByNumber(Long blockNumber);

    void getBlockByHash(String blockHash);

    void getBlockReceiptByHash(String blockHash);

    ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByNumber(Long blockNumber);

    ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByHash(String blockHash);

    ResGetBlockTransactionCountDTO getBlockTransactionCountByNumber(Long blockNumber);

    ResGetBlockTransactionCountDTO getBlockTransactionCountByHash(String blockHash);

    ResCreateBlockDTO createBlockByNumber(ReqCreateBlockDTO reqCreateBlockDTO);


}
