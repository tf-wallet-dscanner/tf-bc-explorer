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

        List<ResTransactionDTO.Result.TransactionInResult> transactionsInResult = new ArrayList<>();
        for(Transaction.Result.TransactionInResult transactionInResult : transaction.getResult().getTransactions()) {

            List<ResTransactionDTO.Result.TransactionInResult.FeePayerSignature> feePayerSignatures = new ArrayList<>();
            for(Transaction.Result.TransactionInResult.FeePayerSignature feePayerSignature : transactionInResult.getFeePayerSignatures()) {

                feePayerSignatures.add(ResTransactionDTO.Result.TransactionInResult.FeePayerSignature.builder()
                        .v(feePayerSignature.getV())
                        .r(feePayerSignature.getR())
                        .s(feePayerSignature.getS())
                        .build());
            }

            List<ResTransactionDTO.Result.TransactionInResult.Log> logs = new ArrayList<>();
            for(Transaction.Result.TransactionInResult.Log log : transactionInResult.getLogs()) {

                logs.add(ResTransactionDTO.Result.TransactionInResult.Log.builder()
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

            List<ResTransactionDTO.Result.TransactionInResult.Signature> signatures = new ArrayList<>();
            for(Transaction.Result.TransactionInResult.Signature signature : transactionInResult.getSignatures()) {

                signatures.add(ResTransactionDTO.Result.TransactionInResult.Signature.builder()
                        .v(signature.getV())
                        .r(signature.getR())
                        .s(signature.getS())
                        .build());
            }

            transactionsInResult.add(ResTransactionDTO.Result.TransactionInResult.builder()
                    .blockHash(transactionInResult.getBlockHash())
                    .blockNumber(transactionInResult.getBlockNumber())
                    .codeFormat(transactionInResult.getCodeFormat())
                    .contractAddress(transactionInResult.getContractAddress())
                    .feePayer(transactionInResult.getFeePayer())
                    .feePayerSignatures(feePayerSignatures)
                    .feeRatio(transactionInResult.getFeeRatio())
                    .from(transactionInResult.getFrom())
                    .gas(transactionInResult.getGas())
                    .gasPrice(transactionInResult.getGasPrice())
                    .gasPriceToFormat(transactionInResult.getGasPriceToFormat())
                    .gasUsed(transactionInResult.getGasUsed())
                    .txFee(transactionInResult.getTxFee())
                    .key(transactionInResult.getKey())
                    .input(transactionInResult.getInput())
                    .logs(logs)
                    .logsBloom(transactionInResult.getLogsBloom())
                    .nonce(transactionInResult.getNonce())
                    .senderTxHash(transactionInResult.getSenderTxHash())
                    .signatures(signatures)
                    .status(transactionInResult.getStatus())
                    .to(transactionInResult.getTo())
                    .transactionIndex(transactionInResult.getTransactionIndex())
                    .transactionHash(transactionInResult.getTransactionHash())
                    .txError(transactionInResult.getTxError())
                    .type(transactionInResult.getType())
                    .typeInt(transactionInResult.getTypeInt())
                    .value(transactionInResult.getValue())
                    .amount(transactionInResult.getAmount())
                    .methodSig(transactionInResult.getMethodSig())
                    .build()
            );
        }

        ResTransactionDTO.Result result = ResTransactionDTO.Result.builder()
                .blockNumber(transaction.getResult().getBlockNumber())
                .blockHash(transaction.getResult().getBlockHash())
                .transactions(transactionsInResult)
                .build();

        return ResTransactionDTO.builder()
                .id(transaction.getId())
                .jsonrpc(transaction.getJsonrpc())
                .result(result)
                .createAt(transaction.getCreateAt())
                .build();
    }
}
