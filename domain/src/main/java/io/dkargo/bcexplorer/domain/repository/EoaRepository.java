package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.Eoa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EoaRepository extends MongoRepository<Eoa, String> {


    // result/from,to 값을 가지고 Eoa 조회
    Eoa findByResult_address(String address);
}
