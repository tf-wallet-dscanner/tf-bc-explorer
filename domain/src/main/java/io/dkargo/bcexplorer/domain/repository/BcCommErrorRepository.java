package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.BcCommError;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BcCommErrorRepository extends MongoRepository<BcCommError, String> {

}
