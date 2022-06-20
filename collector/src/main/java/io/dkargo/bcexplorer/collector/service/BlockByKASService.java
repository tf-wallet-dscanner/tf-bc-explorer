package io.dkargo.bcexplorer.collector.service;

public interface BlockByKASService {

    void getLatestBlockNumber();

    void getBlockByBlockNumber(Long blockNumber);

    void getBlockReceiptByBlockHash(String blockHash);
}
