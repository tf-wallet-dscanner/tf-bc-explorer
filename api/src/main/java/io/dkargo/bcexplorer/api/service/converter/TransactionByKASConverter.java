package io.dkargo.bcexplorer.api.service.converter;

import io.dkargo.bcexplorer.domain.entity.Transaction;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.response.ResTransactionDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TransactionByKASConverter {

    // transaction -> res
    public static ResTransactionDTO of(Transaction transaction) {

        List<ResTransactionDTO.Result.FeePayerSignature> feePayerSignatures = new ArrayList<>();
        for(Transaction.Result.FeePayerSignature feePayerSignature : transaction.getResult().getFeePayerSignatures()) {

            feePayerSignatures.add(ResTransactionDTO.Result.FeePayerSignature.builder()
                    .v(feePayerSignature.getV())
                    .r(feePayerSignature.getR())
                    .s(feePayerSignature.getS())
                    .build());
        }

        List<ResTransactionDTO.Result.Log> logs = new ArrayList<>();
        for(Transaction.Result.Log log : transaction.getResult().getLogs()) {

            logs.add(ResTransactionDTO.Result.Log.builder()
                    .logIndex(log.getLogIndex())
                    .transactionIndex(log.getTransactionIndex())
                    .transactionHash(log.getTransactionHash())
                    .blockHash(log.getBlockHash())
                    .blockNumber(log.getBlockNumber())
                    .address(log.getAddress())
                    .data(log.getData())
                    .topics(log.getTopics())
                    .logIndexRaw(log.getLogIndexRaw())
                    .transactionIndexRaw(log.getTransactionIndexRaw())
                    .blockNumberRaw(log.getBlockNumberRaw())
                    .build());
        }

        List<ResTransactionDTO.Result.Signature> signatures = new ArrayList<>();
        for(Transaction.Result.Signature signature : transaction.getResult().getSignatures()) {

            signatures.add(ResTransactionDTO.Result.Signature.builder()
                    .v(signature.getV())
                    .r(signature.getR())
                    .s(signature.getS())
                    .build());
        }

        ResTransactionDTO.Result result = ResTransactionDTO.Result.builder()
                .blockHash(transaction.getResult().getBlockHash())
                .blockNumber(transaction.getResult().getBlockNumber())
                .codeFormat(transaction.getResult().getCodeFormat())
                .contractAddress(transaction.getResult().getContractAddress())
                .feePayer(transaction.getResult().getFeePayer())
                .feePayerSignatures(feePayerSignatures)
                .feeRatio(transaction.getResult().getFeeRatio())
                .from(transaction.getResult().getFrom())
                .gas(transaction.getResult().getGas())
                .gasPrice(transaction.getResult().getGasPrice())
                .gasPriceToFormat(transaction.getResult().getGasPriceToFormat())
                .gasUsed(transaction.getResult().getGasUsed())
                .txFee(transaction.getResult().getTxFee())
                .key(transaction.getResult().getKey())
                .input(transaction.getResult().getInput())
                .logs(logs)
                .logsBloom(transaction.getResult().getLogsBloom())
                .nonce(transaction.getResult().getNonce())
                .senderTxHash(transaction.getResult().getSenderTxHash())
                .signatures(signatures)
                .status(transaction.getResult().getStatus())
                .to(transaction.getResult().getTo())
                .transactionIndex(transaction.getResult().getTransactionIndex())
                .transactionHash(transaction.getResult().getTransactionHash())
                .txError(transaction.getResult().getTxError())
                .type(transaction.getResult().getType())
                .typeInt(transaction.getResult().getTypeInt())
                .value(transaction.getResult().getValue())
                .amount(transaction.getResult().getAmount())
                .methodSig(transaction.getResult().getMethodSig())
                .build();

        return ResTransactionDTO.builder()
                .id(transaction.getId())
                .jsonrpc(transaction.getJsonrpc())
                .result(result)
                .createAt(transaction.getCreateAt())
                .build();
    }
}
