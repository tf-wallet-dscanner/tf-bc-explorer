package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoByHashDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoByNumberDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoBySchedulerDTO;
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

    ResCreateBlockChainInfoDTO createBlockChainInfoByNumber(ReqCreateBlockChainInfoByNumberDTO reqCreateBlockChainInfoByNumberDTO);

//    ResCreateBlockChainInfoDTO createBlockChainInfoByHash(ReqCreateBlockChainInfoByHashDTO reqCreateBlockByHashDTO);

    ResCreateBlockChainInfoDTO createBlockChainInfoByScheduler(ReqCreateBlockChainInfoBySchedulerDTO reqCreateBlockChainInfoBySchedulerDTO);


}
