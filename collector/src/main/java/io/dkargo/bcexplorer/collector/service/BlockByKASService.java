package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.collector.response.ResGetBlockTransactionCountDTO;
import io.dkargo.bcexplorer.dto.collector.response.ResGetLatestBlockNumberDTO;

public interface BlockByKASService {

    ResGetLatestBlockNumberDTO getLatestBlockNumber();

    ResGetBlockDTO getBlockByNumber(Long blockNumber);

    void getBlockByHash(String blockHash);

    void getBlockReceiptByHash(String blockHash);

    void getBlockWithConsensusInfoByNumber(Long blockNumber);

    void getBlockWithConsensusInfoByHash(String blockHash);

    ResGetBlockTransactionCountDTO getBlockTransactionCountByNumber(Long blockNumber);

    ResGetBlockTransactionCountDTO getBlockTransactionCountByHash(String blockHash);


}
