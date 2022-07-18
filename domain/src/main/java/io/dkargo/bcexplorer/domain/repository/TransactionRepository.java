package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    // [--------------- api module ---------------]

    // result/transactionHash 값을 가지고 해당 transaction 조회
    Transaction findByResult_TransactionHash(String transactionHash);

    // 모든 transaction list 조회
    Page<Transaction> findAllBy(Pageable pageable);

    // result/blockNumber 값에 해당하는 모든 transaction list 조회
    Page<Transaction> findByResult_BlockNumber(String blockNumber, Pageable pageable);

}
