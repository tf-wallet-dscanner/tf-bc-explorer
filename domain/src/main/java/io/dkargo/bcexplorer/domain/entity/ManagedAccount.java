package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "managedaccounts")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ManagedAccount {

    @Id
    private String id;

    private String serviceCode;

    private String address;

    // EOA/SCA
    private String accountType;
}
