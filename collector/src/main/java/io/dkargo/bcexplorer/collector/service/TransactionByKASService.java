package io.dkargo.bcexplorer.collector.service;

public interface TransactionByKASService {

    void getTransactionByHash(String hash);

    void getTransactionReceiptByHash(String transactionHash);
}
