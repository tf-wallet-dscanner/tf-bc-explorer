package io.dkargo.bcexplorer.domain.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "Test")
public class Test {

    @Id
    private ObjectId id;

    private String name;
}
