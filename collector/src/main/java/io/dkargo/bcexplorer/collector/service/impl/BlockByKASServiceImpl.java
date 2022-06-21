package io.dkargo.bcexplorer.collector.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klaytn.caver.methods.response.*;
import com.klaytn.caver.wallet.keyring.SignatureData;
import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.FeePayerSignaturesObj;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockByKASServiceImpl implements BlockByKASService {

    private final CaverExtKAS caverExtKAS;

    public static String objectToString(Object object) {

        String objectToString = null;

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            objectToString = ow.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectToString;
    }

    public static Long hexToLong(String hexadecimal) {

        return Long.decode(hexadecimal);
    }

    public static String timestampToString(Long timestamp) {

        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(date);
    }

    public static ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfo(BlockWithConsensusInfo blockWithConsensusInfo) {

        ResGetBlockWithConsensusInfoDTO.Result result = null;
        if(blockWithConsensusInfo.getResult() != null) {

            // result -> transactions 생성
            List<ResGetBlockWithConsensusInfoDTO.Result.Transactions> transactions = new ArrayList<>();
            if(blockWithConsensusInfo.getResult().getTransactions() != null) {
                for(Transaction.TransactionData transactionData : blockWithConsensusInfo.getResult().getTransactions()) {

                    // result -> transactions -> feePayerSignatures 생성
                    List<ResGetBlockWithConsensusInfoDTO.Result.Transactions.FeePayerSignatures> feePayerSignatures = new ArrayList<>();
                    if (transactionData.getFeePayerSignatures() != null) {
                        for(SignatureData signatureData : transactionData.getFeePayerSignatures()) {

                            feePayerSignatures.add(
                                    ResGetBlockWithConsensusInfoDTO.Result.Transactions.FeePayerSignatures.builder()
                                            .v(signatureData.getV())
                                            .r(signatureData.getR())
                                            .s(signatureData.getS())
                                            .build()
                            );
                        }
                    }

                    // result -> transactions -> signatures 생성
                    List<ResGetBlockWithConsensusInfoDTO.Result.Transactions.Signatures> signatures = new ArrayList<>();
                    for(SignatureData signatureData : transactionData.getSignatures()) {

                        signatures.add(
                                ResGetBlockWithConsensusInfoDTO.Result.Transactions.Signatures.builder()
                                        .v(signatureData.getV())
                                        .r(signatureData.getR())
                                        .s(signatureData.getS())
                                        .build()
                        );
                    }

                    transactions.add(ResGetBlockWithConsensusInfoDTO.Result.Transactions.builder()
                            .blockHash(transactionData.getBlockHash())
                            .blockNumber(transactionData.getBlockNumber())
                            .codeFormat(transactionData.getCodeFormat())
                            .feePayer(transactionData.getFeePayer())
                            .feePayerSignatures(feePayerSignatures)
                            .feeRatio(transactionData.getFeeRatio())
                            .from(transactionData.getFrom())
                            .gas(transactionData.getGas())
                            .gasPrice(transactionData.getGasPrice())
                            .hash(transactionData.getHash())
                            .key(transactionData.getKey())
                            .input(transactionData.getInput())
                            .nonce(transactionData.getNonce())
                            .senderTxHash(transactionData.getSenderTxHash())
                            .signatures(signatures)
                            .to(transactionData.getTo())
                            .transactionIndex(transactionData.getTransactionIndex())
                            .type(transactionData.getType())
                            .typeInt(transactionData.getTypeInt())
                            .value(transactionData.getValue())
                            .build()
                    );
                }
            }

            // result 생성
            result = ResGetBlockWithConsensusInfoDTO.Result.builder()
                    .blockScore(blockWithConsensusInfo.getResult().getBlockScore())
                    .totalBlockScore(blockWithConsensusInfo.getResult().getTotalBlockScore())
                    .committee(blockWithConsensusInfo.getResult().getCommittee())
                    .gasLimit(blockWithConsensusInfo.getResult().getGasLimit())
                    .gasUsed(blockWithConsensusInfo.getResult().getGasUsed())
                    .hash(blockWithConsensusInfo.getResult().getHash())
                    .miner(blockWithConsensusInfo.getResult().getMiner())
                    .nonce(blockWithConsensusInfo.getResult().getNonce())
                    .number(blockWithConsensusInfo.getResult().getNumber())
                    .parentBlockHash(blockWithConsensusInfo.getResult().getParentHash())
                    .proposer(blockWithConsensusInfo.getResult().getProposer())
                    .receiptsRoot(blockWithConsensusInfo.getResult().getReceiptsRoot())
                    .size(blockWithConsensusInfo.getResult().getSize())
                    .stateRoot(blockWithConsensusInfo.getResult().getStateRoot())
                    .timestamp(blockWithConsensusInfo.getResult().getTimestamp())
                    .timestampFoS(blockWithConsensusInfo.getResult().getTimestampFoS())
                    .transactions(transactions)
                    .transactionsRoot(blockWithConsensusInfo.getResult().getTransactionsRoot())
                    .build();
        }

        // error 생성
        ResGetBlockWithConsensusInfoDTO.Error error = null;
        if (blockWithConsensusInfo.getError() != null) {
            error = ResGetBlockWithConsensusInfoDTO.Error.builder()
                    .code(blockWithConsensusInfo.getError().getCode())
                    .message(blockWithConsensusInfo.getError().getMessage())
                    .data(blockWithConsensusInfo.getError().getData())
                    .build();
        }

        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = ResGetBlockWithConsensusInfoDTO.builder()
                .id(blockWithConsensusInfo.getId())
                .jsonrpc(blockWithConsensusInfo.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(blockWithConsensusInfo.getRawResponse())
                .build();

        return resGetBlockWithConsensusInfoDTO;
    }

    @Override
    public ResGetLatestBlockNumberDTO getLatestBlockNumber() {

        long blockNumber = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockNumber().send();
            log.info("quantity : {}", objectToString(quantity));

            blockNumber = hexToLong(quantity.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetLatestBlockNumberDTO(blockNumber);
    }

    @Override
    public ResGetBlockDTO getBlockByNumber(Long blockNumber) {

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByNumber(blockNumber).send();
            log.info("block : {}", objectToString(block));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockDTO(blockNumber);
    }

    @Override
    public void getBlockByHash(String blockHash) {

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByHash(blockHash).send();
            log.info("block : {}", objectToString(block));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBlockReceiptByHash(String blockHash) {

        try {
            BlockTransactionReceipts blockTransactionReceipts = caverExtKAS.rpc.klay.getBlockReceipts(blockHash).send();
            log.info("blockTransactionReceipts : {}", objectToString(blockTransactionReceipts));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByNumber(Long blockNumber) {

        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = null;

        try {
            BlockWithConsensusInfo blockWithConsensusInfo = caverExtKAS.rpc.klay.getBlockWithConsensusInfoByNumber(blockNumber).send();
            log.info("blockWithConsensusInfo : {}", objectToString(blockWithConsensusInfo));

            ResGetBlockWithConsensusInfoDTO.Result result = null;
            if(blockWithConsensusInfo.getResult() != null) {

                // result -> transactions 생성
                List<ResGetBlockWithConsensusInfoDTO.Result.Transactions> transactions = new ArrayList<>();
                if(blockWithConsensusInfo.getResult().getTransactions() != null) {
                    for(Transaction.TransactionData transactionData : blockWithConsensusInfo.getResult().getTransactions()) {

                        // result -> transactions -> feePayerSignatures 생성
                        List<ResGetBlockWithConsensusInfoDTO.Result.Transactions.FeePayerSignatures> feePayerSignatures = new ArrayList<>();
                        if (transactionData.getFeePayerSignatures() != null) {
                            for(SignatureData signatureData : transactionData.getFeePayerSignatures()) {

                                feePayerSignatures.add(
                                        ResGetBlockWithConsensusInfoDTO.Result.Transactions.FeePayerSignatures.builder()
                                                .v(signatureData.getV())
                                                .r(signatureData.getR())
                                                .s(signatureData.getS())
                                                .build()
                                );
                            }
                        }

                        // result -> transactions -> signatures 생성
                        List<ResGetBlockWithConsensusInfoDTO.Result.Transactions.Signatures> signatures = new ArrayList<>();
                        for(SignatureData signatureData : transactionData.getSignatures()) {

                            signatures.add(
                                    ResGetBlockWithConsensusInfoDTO.Result.Transactions.Signatures.builder()
                                            .v(signatureData.getV())
                                            .r(signatureData.getR())
                                            .s(signatureData.getS())
                                            .build()
                            );
                        }

                        transactions.add(ResGetBlockWithConsensusInfoDTO.Result.Transactions.builder()
                                .blockHash(transactionData.getBlockHash())
                                .blockNumber(transactionData.getBlockNumber())
                                .codeFormat(transactionData.getCodeFormat())
                                .feePayer(transactionData.getFeePayer())
                                .feePayerSignatures(feePayerSignatures)
                                .feeRatio(transactionData.getFeeRatio())
                                .from(transactionData.getFrom())
                                .gas(transactionData.getGas())
                                .gasPrice(transactionData.getGasPrice())
                                .hash(transactionData.getHash())
                                .key(transactionData.getKey())
                                .input(transactionData.getInput())
                                .nonce(transactionData.getNonce())
                                .senderTxHash(transactionData.getSenderTxHash())
                                .signatures(signatures)
                                .to(transactionData.getTo())
                                .transactionIndex(transactionData.getTransactionIndex())
                                .type(transactionData.getType())
                                .typeInt(transactionData.getTypeInt())
                                .value(transactionData.getValue())
                                .build()
                        );
                    }
                }

                // result 생성
                result = ResGetBlockWithConsensusInfoDTO.Result.builder()
                        .blockScore(blockWithConsensusInfo.getResult().getBlockScore())
                        .totalBlockScore(blockWithConsensusInfo.getResult().getTotalBlockScore())
                        .committee(blockWithConsensusInfo.getResult().getCommittee())
                        .gasLimit(blockWithConsensusInfo.getResult().getGasLimit())
                        .gasUsed(blockWithConsensusInfo.getResult().getGasUsed())
                        .hash(blockWithConsensusInfo.getResult().getHash())
                        .miner(blockWithConsensusInfo.getResult().getMiner())
                        .nonce(blockWithConsensusInfo.getResult().getNonce())
                        .number(blockWithConsensusInfo.getResult().getNumber())
                        .parentBlockHash(blockWithConsensusInfo.getResult().getParentHash())
                        .proposer(blockWithConsensusInfo.getResult().getProposer())
                        .receiptsRoot(blockWithConsensusInfo.getResult().getReceiptsRoot())
                        .size(blockWithConsensusInfo.getResult().getSize())
                        .stateRoot(blockWithConsensusInfo.getResult().getStateRoot())
                        .timestamp(blockWithConsensusInfo.getResult().getTimestamp())
                        .timestampFoS(blockWithConsensusInfo.getResult().getTimestampFoS())
                        .transactions(transactions)
                        .transactionsRoot(blockWithConsensusInfo.getResult().getTransactionsRoot())
                        .build();
            }

            // error 생성
            ResGetBlockWithConsensusInfoDTO.Error error = null;
            if (blockWithConsensusInfo.getError() != null) {
                error = ResGetBlockWithConsensusInfoDTO.Error.builder()
                        .code(blockWithConsensusInfo.getError().getCode())
                        .message(blockWithConsensusInfo.getError().getMessage())
                        .data(blockWithConsensusInfo.getError().getData())
                        .build();
            }

            resGetBlockWithConsensusInfoDTO = ResGetBlockWithConsensusInfoDTO.builder()
                    .id(blockWithConsensusInfo.getId())
                    .jsonrpc(blockWithConsensusInfo.getJsonrpc())
                    .result(result)
                    .error(error)
                    .rawResponse(blockWithConsensusInfo.getRawResponse())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetBlockWithConsensusInfoDTO;
    }

    @Override
    public ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByHash(String blockHash) {

        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = null;

        try {
            BlockWithConsensusInfo blockWithConsensusInfo = caverExtKAS.rpc.klay.getBlockWithConsensusInfoByHash(blockHash).send();
            log.info("blockWithConsensusInfo : {}", objectToString(blockWithConsensusInfo));

            ResGetBlockWithConsensusInfoDTO.Result result = null;
            if(blockWithConsensusInfo.getResult() != null) {

                // result -> transactions 생성
                List<ResGetBlockWithConsensusInfoDTO.Result.Transactions> transactions = new ArrayList<>();
                if(blockWithConsensusInfo.getResult().getTransactions() != null) {
                    for(Transaction.TransactionData transactionData : blockWithConsensusInfo.getResult().getTransactions()) {

                        // result -> transactions -> feePayerSignatures 생성
                        List<ResGetBlockWithConsensusInfoDTO.Result.Transactions.FeePayerSignatures> feePayerSignatures = new ArrayList<>();
                        if (transactionData.getFeePayerSignatures() != null) {
                            for(SignatureData signatureData : transactionData.getFeePayerSignatures()) {

                                feePayerSignatures.add(
                                        ResGetBlockWithConsensusInfoDTO.Result.Transactions.FeePayerSignatures.builder()
                                                .v(signatureData.getV())
                                                .r(signatureData.getR())
                                                .s(signatureData.getS())
                                                .build()
                                );
                            }
                        }

                        // result -> transactions -> signatures 생성
                        List<ResGetBlockWithConsensusInfoDTO.Result.Transactions.Signatures> signatures = new ArrayList<>();
                        for(SignatureData signatureData : transactionData.getSignatures()) {

                            signatures.add(
                                    ResGetBlockWithConsensusInfoDTO.Result.Transactions.Signatures.builder()
                                            .v(signatureData.getV())
                                            .r(signatureData.getR())
                                            .s(signatureData.getS())
                                            .build()
                            );
                        }

                        transactions.add(ResGetBlockWithConsensusInfoDTO.Result.Transactions.builder()
                                .blockHash(transactionData.getBlockHash())
                                .blockNumber(transactionData.getBlockNumber())
                                .codeFormat(transactionData.getCodeFormat())
                                .feePayer(transactionData.getFeePayer())
                                .feePayerSignatures(feePayerSignatures)
                                .feeRatio(transactionData.getFeeRatio())
                                .from(transactionData.getFrom())
                                .gas(transactionData.getGas())
                                .gasPrice(transactionData.getGasPrice())
                                .hash(transactionData.getHash())
                                .key(transactionData.getKey())
                                .input(transactionData.getInput())
                                .nonce(transactionData.getNonce())
                                .senderTxHash(transactionData.getSenderTxHash())
                                .signatures(signatures)
                                .to(transactionData.getTo())
                                .transactionIndex(transactionData.getTransactionIndex())
                                .type(transactionData.getType())
                                .typeInt(transactionData.getTypeInt())
                                .value(transactionData.getValue())
                                .build()
                        );
                    }
                }

                // result 생성
                result = ResGetBlockWithConsensusInfoDTO.Result.builder()
                        .blockScore(blockWithConsensusInfo.getResult().getBlockScore())
                        .totalBlockScore(blockWithConsensusInfo.getResult().getTotalBlockScore())
                        .committee(blockWithConsensusInfo.getResult().getCommittee())
                        .gasLimit(blockWithConsensusInfo.getResult().getGasLimit())
                        .gasUsed(blockWithConsensusInfo.getResult().getGasUsed())
                        .hash(blockWithConsensusInfo.getResult().getHash())
                        .miner(blockWithConsensusInfo.getResult().getMiner())
                        .nonce(blockWithConsensusInfo.getResult().getNonce())
                        .number(blockWithConsensusInfo.getResult().getNumber())
                        .parentBlockHash(blockWithConsensusInfo.getResult().getParentHash())
                        .proposer(blockWithConsensusInfo.getResult().getProposer())
                        .receiptsRoot(blockWithConsensusInfo.getResult().getReceiptsRoot())
                        .size(blockWithConsensusInfo.getResult().getSize())
                        .stateRoot(blockWithConsensusInfo.getResult().getStateRoot())
                        .timestamp(blockWithConsensusInfo.getResult().getTimestamp())
                        .timestampFoS(blockWithConsensusInfo.getResult().getTimestampFoS())
                        .transactions(transactions)
                        .transactionsRoot(blockWithConsensusInfo.getResult().getTransactionsRoot())
                        .build();
            }

            // error 생성
            ResGetBlockWithConsensusInfoDTO.Error error = null;
            if (blockWithConsensusInfo.getError() != null) {
                error = ResGetBlockWithConsensusInfoDTO.Error.builder()
                        .code(blockWithConsensusInfo.getError().getCode())
                        .message(blockWithConsensusInfo.getError().getMessage())
                        .data(blockWithConsensusInfo.getError().getData())
                        .build();
            }

            resGetBlockWithConsensusInfoDTO = ResGetBlockWithConsensusInfoDTO.builder()
                    .id(blockWithConsensusInfo.getId())
                    .jsonrpc(blockWithConsensusInfo.getJsonrpc())
                    .result(result)
                    .error(error)
                    .rawResponse(blockWithConsensusInfo.getRawResponse())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetBlockWithConsensusInfoDTO;
    }

    @Override
    public ResGetBlockTransactionCountDTO getBlockTransactionCountByNumber(Long blockNumber) {

        long transactionCount = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockTransactionCountByNumber(blockNumber).send();
            log.info("quantity : {}", objectToString(quantity));

            transactionCount = hexToLong(quantity.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockTransactionCountDTO(transactionCount);
    }

    @Override
    public ResGetBlockTransactionCountDTO getBlockTransactionCountByHash(String blockHash) {

        long transactionCount = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockTransactionCountByHash(blockHash).send();
            log.info("quantity : {}", objectToString(quantity));

            transactionCount = hexToLong(quantity.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockTransactionCountDTO(transactionCount);
    }

    @Override
    public ResCreateBlockDTO createBlockByNumber(ReqCreateBlockDTO reqCreateBlockDTO) {

        getBlockByNumber(reqCreateBlockDTO.getBlockNumber());
        return new ResCreateBlockDTO(1004L, "good");
    }
}
