package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    // [--------------- api module ---------------]

    // result/transactionHash 값을 가지고 해당 transaction 조회
    Transaction findByResult_TransactionHash(String transactionHash);

    // 페이징에 맞는 transaction list 조회
    Page<Transaction> findAllBy(Pageable pageable);

    // result/blockNumber 값에 해당하는 페이징에 맞는 transaction list 조회
    Page<Transaction> findAllByResult_BlockNumber(String blockNumber, Pageable pageable);

    // result/from 혹은 to 값에 해당하는 모든 transaction list 조회
    List<Transaction> findAllByResult_FromOrResult_To(String address1, String address2);

}
