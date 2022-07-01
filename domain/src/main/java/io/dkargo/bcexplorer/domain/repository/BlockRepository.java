package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Block;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockRepository extends MongoRepository<Block, Long> {

    // [collector]
    // 블록 정보 중 blockNumber 값이 가장 큰 값 1개 조회
    Block findTop1ByOrderByResult_NumberDesc();

    // [api]
    Block findByResult_Number(String blockNumber);

}
