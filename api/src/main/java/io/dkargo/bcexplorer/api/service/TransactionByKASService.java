package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListByBlockNumberDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListDTO;

public interface TransactionByKASService {

    ResGetTransactionDTO getTransactionByHash(String transactionHash);

    ResGetTransactionListDTO getTransactionList();

    ResGetTransactionListByBlockNumberDTO getTransactionListByBlockNumber(Long blockNumber);
}
