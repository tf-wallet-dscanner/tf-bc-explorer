package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.ManagedAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ManagedAccountRepository extends MongoRepository<ManagedAccount, String> {

    List<ManagedAccount> findAllByServiceCode(String serviceCode);
}
