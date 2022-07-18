package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Eoa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EoaRepository extends MongoRepository<Eoa, Long> {


}
