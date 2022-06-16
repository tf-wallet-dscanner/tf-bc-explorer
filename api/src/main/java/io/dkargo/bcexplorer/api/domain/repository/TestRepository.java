package io.dkargo.bcexplorer.api.domain.repository;

import io.dkargo.bcexplorer.api.domain.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String> {

    List<Test> findAllByNameRegex(String name);
}
