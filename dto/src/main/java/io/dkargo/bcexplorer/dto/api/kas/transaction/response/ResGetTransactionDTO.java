package io.dkargo.bcexplorer.dto.api.kas.transaction.response;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.response.ResTransactionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetTransactionDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "transactionHash")
    private String transactionHash;

    @ApiModelProperty(value = "Overview")
    private Overview overview;

    @ApiModelProperty(value = "TxError")
    private String txError;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Overview {

        @ApiModelProperty(value = "TX Type")
        private String txType;

        @ApiModelProperty(value = "Block #")
        private Long blockNumber;

        @ApiModelProperty(value = "From")
        private String from;

        @ApiModelProperty(value = "to")
        private String to;

        @ApiModelProperty(value = "Fee Payer")
        private String feePayer;

        @ApiModelProperty(value = "CreateAt")
        private LocalDateTime createAt;

        @ApiModelProperty(value = "Nonce")
        private Long nonce;

        @ApiModelProperty(value = "Amount")
        private String amount;

        @ApiModelProperty(value = "Gas Price")
        private String gasPrice;

        @ApiModelProperty(value = "Gas Used")
        private Long gasUsed;

        @ApiModelProperty(value = "Gas Limit")
        private Long gasLimit;

        @ApiModelProperty(value = "Tx Fee")
        private String txFee;

        @ApiModelProperty(value = "MethodSig")
        private String methodSig;
    }

    public ResGetTransactionDTO(ResTransactionDTO resTransactionDTO) {

        String feePayer = null;
        if(resTransactionDTO.getResult().getFeePayer() != null) {
            feePayer = resTransactionDTO.getResult().getFeePayer().substring(0, 14);
        }

        Overview overview = Overview.builder()
                .txType(resTransactionDTO.getResult().getType().substring(6).replaceAll("(.)([A-Z])", "$1 $2")) // 띄어 쓰기
                .blockNumber(CommonConverter.hexToLong(resTransactionDTO.getResult().getBlockNumber()))
                .from(resTransactionDTO.getResult().getFrom())
                .to(resTransactionDTO.getResult().getTo())
                .feePayer(feePayer)
                .createAt(CommonConverter.stringToLocalDateTime(resTransactionDTO.getCreateAt()))
                .nonce(CommonConverter.hexToLong(resTransactionDTO.getResult().getNonce()))
                .amount(resTransactionDTO.getResult().getAmount())
                .gasPrice(resTransactionDTO.getResult().getGasPriceToFormat())
                .gasUsed(CommonConverter.hexToLong(resTransactionDTO.getResult().getGasUsed()))
                .gasLimit(CommonConverter.hexToLong(resTransactionDTO.getResult().getGas()))
                .txFee(resTransactionDTO.getResult().getTxFee())
                .methodSig(resTransactionDTO.getResult().getMethodSig())
                .build();

        this.id = resTransactionDTO.getId();
        this.transactionHash = resTransactionDTO.getResult().getTransactionHash();
        this.overview = overview;
        this.txError = resTransactionDTO.getResult().getTxError();
    }
}
