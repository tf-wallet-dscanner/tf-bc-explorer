package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.Transaction;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockReceiptDTO;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.request.ReqTransactionDTO;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.response.ResTransactionDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TransactionByKASConverter {

    // req -> Transaction
    public static Transaction of(ReqTransactionDTO reqTransactionDTO) {

        List<Transaction.Result.TransactionInResult> transactions = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : reqTransactionDTO.getResults()) {

            List<Transaction.Result.TransactionInResult.FeePayerSignature> feePayerSignatures = new ArrayList<>();
            for(ResGetBlockReceiptDTO.Result.FeePayerSignature feePayerSignature : result.getFeePayerSignatures()) {

                feePayerSignatures.add(Transaction.Result.TransactionInResult.FeePayerSignature.builder()
                                .v(feePayerSignature.getV())
                                .r(feePayerSignature.getR())
                                .s(feePayerSignature.getS())
                                .build());
            }

            List<Transaction.Result.TransactionInResult.Log> logs = new ArrayList<>();
            for(ResGetBlockReceiptDTO.Result.Log log : result.getLogs()) {

                logs.add(Transaction.Result.TransactionInResult.Log.builder()
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

            List<Transaction.Result.TransactionInResult.Signature> signatures = new ArrayList<>();
            for(ResGetBlockReceiptDTO.Result.Signature signature : result.getSignatures()) {

                signatures.add(Transaction.Result.TransactionInResult.Signature.builder()
                                .v(signature.getV())
                                .r(signature.getR())
                                .s(signature.getS())
                                .build());
            }

            transactions.add(Transaction.Result.TransactionInResult.builder()
                            .blockHash(result.getBlockHash())
                            .blockNumber(result.getBlockNumber())
                            .codeFormat(result.getCodeFormat())
                            .contractAddress(result.getContractAddress())
                            .feePayer(result.getFeePayer())
                            .feePayerSignatures(feePayerSignatures)
                            .feeRatio(result.getFeeRatio())
                            .from(result.getFrom())
                            .gas(result.getGas())
                            .gasPrice(result.getGasPrice())
                            .gasPriceToFormat(result.getGasPriceByFormat())
                            .gasUsed(result.getGasUsed())
                            .txFee(result.getTxFee())
                            .key(result.getKey())
                            .input(result.getInput())
                            .logs(logs)
                            .logsBloom(result.getLogsBloom())
                            .nonce(result.getNonce())
                            .senderTxHash(result.getSenderTxHash())
                            .signatures(signatures)
                            .status(result.getStatus())
                            .to(result.getTo())
                            .transactionIndex(result.getTransactionIndex())
                            .transactionHash(result.getTransactionHash())
                            .txError(result.getTxError())
                            .type(result.getType())
                            .typeInt(result.getTypeInt())
                            .value(result.getValue())
                            .amount(result.getAmount())
                            .methodSig(result.getMethodSig())
                            .build());
        }

        String blockNumber = reqTransactionDTO.getBlockNumber();
        String blockHash = reqTransactionDTO.getBlockHash();

        Transaction.Result result = Transaction.Result.builder()
                .blockNumber(blockNumber)
                .blockHash(blockHash)
                .transactions(transactions)
                .build();


        return Transaction.builder()
                .jsonrpc(reqTransactionDTO.getJsonrpc())
                .result(result)
                .createAt(CommonConverter.currentDateTime())
                .build();
    }

    // Transaction -> res
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
                    .gasUsed(transactionInResult.getGasUsed())
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
