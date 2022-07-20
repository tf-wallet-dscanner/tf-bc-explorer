package io.dkargo.bcexplorer.domain.repository;

import io.dkargo.bcexplorer.domain.entity.ManagedAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ManagedAccountRepository extends MongoRepository<ManagedAccount, String> {
}
