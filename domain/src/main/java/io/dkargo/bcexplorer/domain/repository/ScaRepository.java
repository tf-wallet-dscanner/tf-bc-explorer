package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Sca;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScaRepository extends MongoRepository<Sca, String> {


}
