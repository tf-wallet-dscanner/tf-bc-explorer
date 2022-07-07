package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Block;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRepository extends MongoRepository<Block, Long>{

    // [--------------- collector module ---------------]

    // [--------------- api module ---------------]

    // result/number 값에 해당 하는 block 조회
    Block findByResult_Number(String blockNumber);

    // 모든 block list 조회
    Page<Block> findAllBy(Pageable pageable);

    // [--------------- common ---------------]

    // 블록 정보 중 blockNumber 값이 가장 큰 값 1개 조회
    Block findTop1ByOrderByResult_NumberDesc();
}
