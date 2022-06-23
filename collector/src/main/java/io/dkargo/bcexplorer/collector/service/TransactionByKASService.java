package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.kas.transaction.response.ResGetTransactionReceiptByHashDTO;

public interface TransactionByKASService {

    void getTransactionByHash(String hash);

    ResGetTransactionReceiptByHashDTO getTransactionReceiptByHash(String transactionHash);
}
