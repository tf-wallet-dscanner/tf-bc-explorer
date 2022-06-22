package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blockerrors")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BlockError {

    @Id
    private String id;

    private Long blockId;

    private String jsonrpc;

    private Integer code;

    private String message;

    private String data;

    private String rawResponse;

    private String block;

    private String createAt;
}
