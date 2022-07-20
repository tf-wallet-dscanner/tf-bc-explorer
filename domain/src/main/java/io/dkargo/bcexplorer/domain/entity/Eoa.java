package io.dkargo.bcexplorer.domain.entity;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqEoaDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "eoas")
@Builder
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Eoa {

    @Id
    private String id;

    private String jsonrpc;

    private Result result;

    private String createAt;

    private String updateAt;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Result {

        private String address;

        private Integer accType;

        private String balance;

        // 계산 값
        private Double balanceToDouble;

        private Boolean humanReadable;

        private String nonce;

        // 계산 값
        private Long totalTransaction;

        private String type;
    }

    public void update(ResGetAccountDTO resGetAccountDTO, Double balanceToDouble, Long totalTransaction) {

        this.result.accType = resGetAccountDTO.getResult().getAccType();
        this.result.balance = resGetAccountDTO.getResult().getAccount().getBalance();
        this.result.balanceToDouble = balanceToDouble;
        this.result.humanReadable = resGetAccountDTO.getResult().getAccount().getHumanReadable();
        this.result.nonce = resGetAccountDTO.getResult().getAccount().getNonce();
        this.result.totalTransaction = totalTransaction;
        this.updateAt = CommonConverter.currentDateTime();
    }

}
