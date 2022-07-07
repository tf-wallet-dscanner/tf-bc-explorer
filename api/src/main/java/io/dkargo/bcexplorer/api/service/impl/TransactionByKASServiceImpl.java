package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.service.TransactionByKASService;
import io.dkargo.bcexplorer.api.service.converter.TransactionByKASConverter;
import io.dkargo.bcexplorer.domain.entity.Transaction;
import io.dkargo.bcexplorer.domain.repository.TransactionRepository;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListByBlockNumberDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListDTO;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.response.ResTransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionByKASServiceImpl implements TransactionByKASService {

    private final TransactionRepository transactionRepository;

    @Override
    public ResGetTransactionDTO getTransactionByHash(String transactionHash) {

        Transaction transaction = transactionRepository.findByResult_TransactionHash(transactionHash);

        ResTransactionDTO resTransactionDTO = TransactionByKASConverter.of(transaction);

        return new ResGetTransactionDTO(resTransactionDTO);
    }

    @Override
    public ResGetTransactionListDTO getTransactionList() {

        ResGetTransactionListDTO resGetTransactionListDTO = ResGetTransactionListDTO.builder().id("gg").build();

        return resGetTransactionListDTO;
    }

    @Override
    public ResGetTransactionListByBlockNumberDTO getTransactionListByBlockNumber(Long blockNumber, Integer page, Integer size) {

//        Pageable pageable = PageRequest.of(page, Sort)
//
//        Transaction transaction = transactionRepository.findByResult_BlockNumber(CommonConverter.longToHex(blockNumber));

        ResGetTransactionListByBlockNumberDTO resGetTransactionListByBlockNumberDTO = ResGetTransactionListByBlockNumberDTO.builder().id("gg").build();

        return resGetTransactionListByBlockNumberDTO;
    }
}
