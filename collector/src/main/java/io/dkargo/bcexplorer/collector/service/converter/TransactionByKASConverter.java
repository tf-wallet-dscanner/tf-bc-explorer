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

        List<Transaction.Result.FeePayerSignature> feePayerSignatures = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result.FeePayerSignature feePayerSignature : reqTransactionDTO.getResult().getFeePayerSignatures()) {

            feePayerSignatures.add(Transaction.Result.FeePayerSignature.builder()
                    .v(feePayerSignature.getV())
                    .r(feePayerSignature.getR())
                    .s(feePayerSignature.getS())
                    .build());
        }

        List<Transaction.Result.Log> logs = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result.Log log : reqTransactionDTO.getResult().getLogs()) {

            logs.add(Transaction.Result.Log.builder()
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

        List<Transaction.Result.Signature> signatures = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result.Signature signature : reqTransactionDTO.getResult().getSignatures()) {

            signatures.add(Transaction.Result.Signature.builder()
                    .v(signature.getV())
                    .r(signature.getR())
                    .s(signature.getS())
                    .build());
        }

        Transaction.Result result =Transaction.Result.builder()
                .blockHash(reqTransactionDTO.getResult().getBlockHash())
                .blockNumber(reqTransactionDTO.getResult().getBlockNumber())
                .codeFormat(reqTransactionDTO.getResult().getCodeFormat())
                .contractAddress(reqTransactionDTO.getResult().getContractAddress())
                .feePayer(reqTransactionDTO.getResult().getFeePayer())
                .feePayerSignatures(feePayerSignatures)
                .feeRatio(reqTransactionDTO.getResult().getFeeRatio())
                .from(reqTransactionDTO.getResult().getFrom())
                .gas(reqTransactionDTO.getResult().getGas())
                .gasPrice(reqTransactionDTO.getResult().getGasPrice())
                .gasPriceToFormat(reqTransactionDTO.getResult().getGasPriceByFormat())
                .gasUsed(reqTransactionDTO.getResult().getGasUsed())
                .txFee(reqTransactionDTO.getResult().getTxFee())
                .key(reqTransactionDTO.getResult().getKey())
                .input(reqTransactionDTO.getResult().getInput())
                .logs(logs)
                .logsBloom(reqTransactionDTO.getResult().getLogsBloom())
                .nonce(reqTransactionDTO.getResult().getNonce())
                .senderTxHash(reqTransactionDTO.getResult().getSenderTxHash())
                .signatures(signatures)
                .status(reqTransactionDTO.getResult().getStatus())
                .to(reqTransactionDTO.getResult().getTo())
                .transactionIndex(reqTransactionDTO.getResult().getTransactionIndex())
                .transactionHash(reqTransactionDTO.getResult().getTransactionHash())
                .txError(reqTransactionDTO.getResult().getTxError())
                .type(reqTransactionDTO.getResult().getType())
                .typeInt(reqTransactionDTO.getResult().getTypeInt())
                .value(reqTransactionDTO.getResult().getValue())
                .amount(reqTransactionDTO.getResult().getAmount())
                .methodSig(reqTransactionDTO.getResult().getMethodSig())
                .build();

        return Transaction.builder()
                .jsonrpc(reqTransactionDTO.getJsonrpc())
                .result(result)
                .serviceCode(reqTransactionDTO.getServiceCode())
                .createAt(CommonConverter.currentDateTime())
                .build();
    }

    // Transaction -> res
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
                .serviceCode(transaction.getServiceCode())
                .createAt(transaction.getCreateAt())
                .build();
    }
}
