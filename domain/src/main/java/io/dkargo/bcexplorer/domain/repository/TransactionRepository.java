package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, Long> {

    // result/transactionHash 값을 가지고 해당 transaction 조회
    Transaction findByResult_TransactionHash(String transactionHash);

//    Transaction findByResult_BlockNumber(String blockNumber, Pageable pageable);

}
