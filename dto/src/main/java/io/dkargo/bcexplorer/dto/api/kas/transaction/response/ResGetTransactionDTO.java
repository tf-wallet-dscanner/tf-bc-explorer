package io.dkargo.bcexplorer.dto.api.kas.transaction.response;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.request.ReqTransactionDTO;
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

    public ResGetTransactionDTO(ResTransactionDTO resTransactionDTO, String transactionHash) {

        ResTransactionDTO.Result.TransactionInResult transactionInResult = null;

        for(ResTransactionDTO.Result.TransactionInResult transaction : resTransactionDTO.getResult().getTransactions()) {

            if(transaction.getTransactionHash().equals(transactionHash)){
                transactionInResult = ResTransactionDTO.Result.TransactionInResult.builder()
                        .blockHash(transaction.getBlockHash())
                        .blockNumber(transaction.getBlockNumber())
                        .codeFormat(transaction.getCodeFormat())
                        .contractAddress(transaction.getContractAddress())
                        .feePayer(transaction.getFeePayer())
                        .feePayerSignatures(transaction.getFeePayerSignatures())
                        .feeRatio(transaction.getFeeRatio())
                        .from(transaction.getFrom())
                        .gas(transaction.getGas())
                        .gasPrice(transaction.getGasPrice())
                        .gasPriceToFormat(transaction.getGasPriceToFormat())
                        .gasUsed(transaction.getGasUsed())
                        .txFee(transaction.getTxFee())
                        .key(transaction.getKey())
                        .input(transaction.getInput())
                        .logs(transaction.getLogs())
                        .logsBloom(transaction.getLogsBloom())
                        .nonce(transaction.getNonce())
                        .senderTxHash(transaction.getSenderTxHash())
                        .signatures(transaction.getSignatures())
                        .status(transaction.getStatus())
                        .to(transaction.getTo())
                        .transactionIndex(transaction.getTransactionIndex())
                        .transactionHash(transaction.getTransactionHash())
                        .txError(transaction.getTxError())
                        .type(transaction.getType())
                        .typeInt(transaction.getTypeInt())
                        .value(transaction.getValue())
                        .amount(transaction.getAmount())
                        .methodSig(transaction.getMethodSig())
                        .build();
            }
        }

        String feePayer = null;
        if(transactionInResult.getFeePayer() != null) {
            feePayer = transactionInResult.getFeePayer().substring(0, 14);
        }

        Overview overview = Overview.builder()
                .txType(transactionInResult.getType().substring(6).replaceAll("(.)([A-Z])", "$1 $2")) // 띄어 쓰기
                .blockNumber(CommonConverter.hexToLong(transactionInResult.getBlockNumber()))
                .from(transactionInResult.getFrom())
                .to(transactionInResult.getTo())
                .feePayer(feePayer)
                .createAt(CommonConverter.stringToLocalDateTime(resTransactionDTO.getCreateAt()))
                .nonce(CommonConverter.hexToLong(transactionInResult.getNonce()))
                .amount(transactionInResult.getAmount())
                .gasPrice(transactionInResult.getGasPriceToFormat())
                .gasUsed(CommonConverter.hexToLong(transactionInResult.getGasUsed()))
                .gasLimit(CommonConverter.hexToLong(transactionInResult.getGas()))
                .txFee(transactionInResult.getTxFee())
                .methodSig(transactionInResult.getMethodSig())
                .build();

        this.id = resTransactionDTO.getId();
        this.transactionHash = transactionInResult.getTransactionHash();
        this.overview = overview;
        this.txError = transactionInResult.getTxError();
    }
}
