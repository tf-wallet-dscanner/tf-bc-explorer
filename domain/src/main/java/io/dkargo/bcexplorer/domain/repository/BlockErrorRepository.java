package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.BlockError;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockErrorRepository extends MongoRepository<BlockError, Long> {
}
