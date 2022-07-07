package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.service.TransactionByKASService;
import io.dkargo.bcexplorer.api.service.converter.TransactionByKASConverter;
import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.Transaction;
import io.dkargo.bcexplorer.domain.repository.TransactionRepository;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListByBlockNumberDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListDTO;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.response.ResTransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResGetTransactionListDTO getTransactionList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Transaction> transactionPage = transactionRepository.findAllBy(pageable);

        List<Transaction> transactions = transactionPage.getContent();

        List<ResGetTransactionDTO> resGetTransactionDTOS = new ArrayList<>();
        for(Transaction transaction : transactions) {

            ResGetTransactionDTO resGetTransactionDTO = new ResGetTransactionDTO(TransactionByKASConverter.of(transaction));
            resGetTransactionDTOS.add(resGetTransactionDTO);
        }

        return new ResGetTransactionListDTO(transactionPage.getNumber(), transactionPage.getSize(), transactionPage.getTotalPages(), transactionPage.getTotalElements(), resGetTransactionDTOS);
    }

    @Override
    public ResGetTransactionListByBlockNumberDTO getTransactionListByBlockNumber(Long blockNumber, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Transaction> transactionPage = transactionRepository.findByResult_BlockNumber(CommonConverter.longToHex(blockNumber), pageable);

        List<Transaction>  transactions = transactionPage.getContent();

        List<ResGetTransactionDTO> resGetTransactionDTOS = new ArrayList<>();
        for(Transaction transaction : transactions) {

            ResGetTransactionDTO resGetTransactionDTO = new ResGetTransactionDTO(TransactionByKASConverter.of(transaction));
            resGetTransactionDTOS.add(resGetTransactionDTO);
        }

        return new ResGetTransactionListByBlockNumberDTO(transactionPage.getNumber(), transactionPage.getSize(), transactionPage.getTotalPages(), transactionPage.getTotalElements(), resGetTransactionDTOS);
    }
}
