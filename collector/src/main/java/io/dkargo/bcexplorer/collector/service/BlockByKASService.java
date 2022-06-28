package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByHashDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByNumberDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;

public interface BlockByKASService {

    ResGetLatestBlockNumberDTO getLatestBlockNumber();

    ResGetBlockDTO getBlockByNumber(Long blockNumber);

    ResGetBlockDTO getBlockByHash(String blockHash);

    ResGetBlockReceiptDTO getBlockReceiptByHash(String blockHash);

    ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByNumber(Long blockNumber);

    ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByHash(String blockHash);

    ResGetBlockTransactionCountDTO getBlockTransactionCountByNumber(Long blockNumber);

    ResGetBlockTransactionCountDTO getBlockTransactionCountByHash(String blockHash);

    ResCreateBlockDTO createBlockWithTransactionByNumber(ReqCreateBlockByNumberDTO reqCreateBlockByNumberDTO);

    ResCreateBlockDTO createBlockWithTransactionByHash(ReqCreateBlockByHashDTO reqCreateBlockByHashDTO);


}
