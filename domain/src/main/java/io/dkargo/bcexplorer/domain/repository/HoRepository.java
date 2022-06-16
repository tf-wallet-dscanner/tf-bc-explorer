package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Ho;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HoRepository extends MongoRepository<Ho, String> {
}
