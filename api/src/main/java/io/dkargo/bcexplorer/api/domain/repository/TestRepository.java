package io.dkargo.bcexplorer.api.domain.repository;

import io.dkargo.bcexplorer.api.domain.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test, String> {
}
