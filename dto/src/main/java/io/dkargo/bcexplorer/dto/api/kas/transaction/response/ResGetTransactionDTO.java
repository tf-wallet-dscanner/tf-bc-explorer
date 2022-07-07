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

        ResTransactionDTO.Result result = ResTransactionDTO.Result.builder()
                .blockHash(resTransactionDTO.getResult().getBlockHash())
                .blockNumber(resTransactionDTO.getResult().getBlockNumber())
                .codeFormat(resTransactionDTO.getResult().getCodeFormat())
                .contractAddress(resTransactionDTO.getResult().getContractAddress())
                .feePayer(resTransactionDTO.getResult().getFeePayer())
                .feePayerSignatures(resTransactionDTO.getResult().getFeePayerSignatures())
                .feeRatio(resTransactionDTO.getResult().getFeeRatio())
                .from(resTransactionDTO.getResult().getFrom())
                .gas(resTransactionDTO.getResult().getGas())
                .gasPrice(resTransactionDTO.getResult().getGasPrice())
                .gasPriceToFormat(resTransactionDTO.getResult().getGasPriceToFormat())
                .gasUsed(resTransactionDTO.getResult().getGasUsed())
                .txFee(resTransactionDTO.getResult().getTxFee())
                .key(resTransactionDTO.getResult().getKey())
                .input(resTransactionDTO.getResult().getInput())
                .logs(resTransactionDTO.getResult().getLogs())
                .logsBloom(resTransactionDTO.getResult().getLogsBloom())
                .nonce(resTransactionDTO.getResult().getNonce())
                .senderTxHash(resTransactionDTO.getResult().getSenderTxHash())
                .signatures(resTransactionDTO.getResult().getSignatures())
                .status(resTransactionDTO.getResult().getStatus())
                .to(resTransactionDTO.getResult().getTo())
                .transactionIndex(resTransactionDTO.getResult().getTransactionIndex())
                .transactionHash(resTransactionDTO.getResult().getTransactionHash())
                .txError(resTransactionDTO.getResult().getTxError())
                .type(resTransactionDTO.getResult().getType())
                .typeInt(resTransactionDTO.getResult().getTypeInt())
                .value(resTransactionDTO.getResult().getValue())
                .amount(resTransactionDTO.getResult().getAmount())
                .methodSig(resTransactionDTO.getResult().getMethodSig())
                .build();

        String feePayer = null;
        if(result.getFeePayer() != null) {
            feePayer = result.getFeePayer().substring(0, 14);
        }

        Overview overview = Overview.builder()
                .txType(result.getType().substring(6).replaceAll("(.)([A-Z])", "$1 $2")) // 띄어 쓰기
                .blockNumber(CommonConverter.hexToLong(result.getBlockNumber()))
                .from(result.getFrom())
                .to(result.getTo())
                .feePayer(feePayer)
                .createAt(CommonConverter.stringToLocalDateTime(resTransactionDTO.getCreateAt()))
                .nonce(CommonConverter.hexToLong(result.getNonce()))
                .amount(result.getAmount())
                .gasPrice(result.getGasPriceToFormat())
                .gasUsed(CommonConverter.hexToLong(result.getGasUsed()))
                .gasLimit(CommonConverter.hexToLong(result.getGas()))
                .txFee(result.getTxFee())
                .methodSig(result.getMethodSig())
                .build();

        this.id = resTransactionDTO.getId();
        this.transactionHash = result.getTransactionHash();
        this.overview = overview;
        this.txError = result.getTxError();
    }
}
