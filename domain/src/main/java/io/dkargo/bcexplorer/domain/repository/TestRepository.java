package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String> {

    List<Test> findAllByNameRegex(String name);
}
