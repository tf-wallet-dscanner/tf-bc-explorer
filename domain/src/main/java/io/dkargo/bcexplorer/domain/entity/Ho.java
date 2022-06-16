package io.dkargo.bcexplorer.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ho")
@Getter
@NoArgsConstructor
public class Ho {

    @Id
    private String id;

    private String name;
}
