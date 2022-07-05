package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, Long> {

    // result/transaction[]/transactionHash 값을 가지고 해당 transaction 조회
    Transaction findByResult_Transactions_TransactionHash(String transactionHash);

}
