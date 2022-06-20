package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.response.ResGetLatestBlockNumberDTO;

public interface BlockByKASService {

    ResGetLatestBlockNumberDTO getLatestBlockNumber();

    void getBlockByBlockNumber(Long blockNumber);

    void getBlockReceiptByBlockHash(String blockHash);
}
