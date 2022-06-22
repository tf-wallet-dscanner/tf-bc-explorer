package io.dkargo.bcexplorer.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "blocks")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Block {

    @Id
    private String id;

    private Long blockId;

    private String jsonrpc;

    private String error;

    private String rawResponse;

    private String blockScore;

    private String totalBlockScore;

    private List<String> committee;

    private String gasLimit;

    private String gasUsed;

    private String hash;

    private String miner;

    private String nonce;

    private String number;

    private String parentBlockHash;

    private String proposer;

    private String receiptsRoot;

    private String size;

    private String stateRoot;

    private String timestamp;

    private String timestampFoS;

    private String transactionsRoot;

    private String createAt;
}
