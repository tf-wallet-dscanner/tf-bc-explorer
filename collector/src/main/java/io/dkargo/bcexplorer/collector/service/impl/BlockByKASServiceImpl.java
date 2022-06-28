package io.dkargo.bcexplorer.collector.service.impl;

import com.klaytn.caver.methods.response.*;
import com.klaytn.caver.wallet.keyring.SignatureData;
import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import io.dkargo.bcexplorer.collector.service.converter.BlockByKASConverter;
import io.dkargo.bcexplorer.collector.service.converter.BlockErrorByKASConverter;
import io.dkargo.bcexplorer.collector.service.converter.CommonConverter;
import io.dkargo.bcexplorer.collector.service.converter.TransactionByKASConverter;
import io.dkargo.bcexplorer.core.error.DkargoException;
import io.dkargo.bcexplorer.core.error.ErrorCodeEnum;
import io.dkargo.bcexplorer.core.type.KASRequestType;
import io.dkargo.bcexplorer.domain.repository.BlockErrorRepository;
import io.dkargo.bcexplorer.domain.repository.BlockRepository;
import io.dkargo.bcexplorer.domain.repository.TransactionRepository;
import io.dkargo.bcexplorer.dto.domain.kas.block.request.ReqBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqBlockErrorDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByHashDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByNumberDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.request.ReqTransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockByKASServiceImpl implements BlockByKASService {

    private final CaverExtKAS caverExtKAS;

    private final BlockRepository blockRepository;
    private final BlockErrorRepository blockErrorRepository;
    private final TransactionRepository transactionRepository;

    public static ResGetBlockDTO getBlock(Block block) {

        ResGetBlockDTO.Result result = null;
        if(block.getResult() != null) {


            // result -> transactions 생성
            List<ResGetBlockDTO.Result.Transaction> transactions = new ArrayList<>();
            if(block.getResult().getTransactions() != null) {

                /**
                 * TODO 1.result -> transactions 생성 (..., feePayerSignatures, signatures)
                 * TODO 2.transactions 에 add
                 */
            }

            // result 생성
            result = ResGetBlockDTO.Result.builder()
                    .number(block.getResult().getNumber())
                    .hash(block.getResult().getHash())
                    .parentHash(block.getResult().getParentHash())
                    .logsBloom(block.getResult().getLogsBloom())
                    .transactionsRoot(block.getResult().getTransactionsRoot())
                    .stateRoot(block.getResult().getStateRoot())
                    .receiptsRoot(block.getResult().getReceiptsRoot())
                    .reward(block.getResult().getReward())
                    .blockScore(block.getResult().getBlockScore())
                    .totalBlockScore(block.getResult().getTotalBlockScore())
                    .extraData(block.getResult().getExtraData())
                    .size(block.getResult().getSize())
                    .gasUsed(block.getResult().getGasUsed())
                    .timestamp(block.getResult().getTimestamp())
                    .timestampFoS(block.getResult().getTimestampFoS())
                    .governanceData(block.getResult().getGovernanceData())
                    .voteData(block.getResult().getVoteData())
                    .transactions(transactions)
                    .transactionCount(block.getResult().getTransactions().size())
                    .build();
        }

        // error 생성
        ResGetBlockDTO.Error error = null;
        if(block.getError() != null) {

            error = ResGetBlockDTO.Error.builder()
                    .code(block.getError().getCode())
                    .message(block.getError().getMessage())
                    .data(block.getError().getData())
                    .build();
        }

        // response 생성
        ResGetBlockDTO resGetBlockDTO = ResGetBlockDTO.builder()
                .id(block.getId())
                .jsonrpc(block.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(block.getRawResponse())
                .build();

        return resGetBlockDTO;
    }

    // block with consensus 정보 조회 포멧
    public static ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfo(BlockWithConsensusInfo blockWithConsensusInfo) {

        ResGetBlockWithConsensusInfoDTO.Result result = null;
        if(blockWithConsensusInfo.getResult() != null) {

            // result -> transactions 생성
            List<ResGetBlockWithConsensusInfoDTO.Result.Transaction> transactions = new ArrayList<>();
            if(blockWithConsensusInfo.getResult().getTransactions() != null) {
                for(Transaction.TransactionData transactionData : blockWithConsensusInfo.getResult().getTransactions()) {

                    // result -> transactions -> feePayerSignatures 생성
                    List<ResGetBlockWithConsensusInfoDTO.Result.Transaction.FeePayerSignature> feePayerSignatures = new ArrayList<>();
                    if (transactionData.getFeePayerSignatures() != null) {
                        for(SignatureData signatureData : transactionData.getFeePayerSignatures()) {

                            feePayerSignatures.add(
                                    ResGetBlockWithConsensusInfoDTO.Result.Transaction.FeePayerSignature.builder()
                                            .v(signatureData.getV())
                                            .r(signatureData.getR())
                                            .s(signatureData.getS())
                                            .build()
                            );
                        }
                    }

                    // result -> transactions -> signatures 생성
                    List<ResGetBlockWithConsensusInfoDTO.Result.Transaction.Signature> signatures = new ArrayList<>();
                    for(SignatureData signatureData : transactionData.getSignatures()) {

                        signatures.add(
                                ResGetBlockWithConsensusInfoDTO.Result.Transaction.Signature.builder()
                                        .v(signatureData.getV())
                                        .r(signatureData.getR())
                                        .s(signatureData.getS())
                                        .build()
                        );
                    }

                    transactions.add(ResGetBlockWithConsensusInfoDTO.Result.Transaction.builder()
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

        // response 생성
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = ResGetBlockWithConsensusInfoDTO.builder()
                .id(blockWithConsensusInfo.getId())
                .jsonrpc(blockWithConsensusInfo.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(blockWithConsensusInfo.getRawResponse())
                .build();

        return resGetBlockWithConsensusInfoDTO;
    }

    // block receipt 정보 조회 포멧
    public static ResGetBlockReceiptDTO getBlockReceipt(BlockTransactionReceipts blockTransactionReceipts) {

        List<ResGetBlockReceiptDTO.Result> results = new ArrayList<>();
        if(blockTransactionReceipts.getResult() != null) {

            // result 개수 만큼 반복
            for(TransactionReceipt.TransactionReceiptData transactionReceiptData : blockTransactionReceipts.getResult()) {

                // result -> feePayerSignatures 생성
                List<ResGetBlockReceiptDTO.Result.FeePayerSignature> feePayerSignatures = new ArrayList<>();
                if(transactionReceiptData.getFeePayerSignatures() != null) {

                    for(SignatureData signatureData : transactionReceiptData.getFeePayerSignatures()) {

                        feePayerSignatures.add(
                            ResGetBlockReceiptDTO.Result.FeePayerSignature.builder()
                                    .v(signatureData.getV())
                                    .r(signatureData.getR())
                                    .s(signatureData.getS())
                                    .build()
                        );
                    }
                }

                // result -> logs 생성
                List<ResGetBlockReceiptDTO.Result.Log> logs = new ArrayList<>();
                if(transactionReceiptData.getLogs() != null) {

                    for(KlayLogs.Log log : transactionReceiptData.getLogs()) {

                        logs.add(
                                ResGetBlockReceiptDTO.Result.Log.builder()
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
                                        .build()
                        );
                    }
                }

                // result -> signatures 생성
                List<ResGetBlockReceiptDTO.Result.Signature> signatures = new ArrayList<>();
                if(transactionReceiptData.getSignatures() != null) {

                    for(SignatureData signatureData : transactionReceiptData.getSignatures()) {

                        signatures.add(
                                ResGetBlockReceiptDTO.Result.Signature.builder()
                                        .v(signatureData.getV())
                                        .r(signatureData.getR())
                                        .s(signatureData.getS())
                                        .build()
                        );
                    }
                }

                // results 생성
                results.add(
                  ResGetBlockReceiptDTO.Result.builder()
                          .blockHash(transactionReceiptData.getBlockHash())
                          .blockNumber(transactionReceiptData.getBlockNumber())
                          .codeFormat(transactionReceiptData.getCodeFormat())
                          .contractAddress(transactionReceiptData.getContractAddress())
                          .feePayer(transactionReceiptData.getFeePayer())
                          .feePayerSignatures(feePayerSignatures)
                          .feeRatio(transactionReceiptData.getFeeRatio())
                          .from(transactionReceiptData.getFrom())
                          .gas(transactionReceiptData.getGas())
                          .gasPrice(transactionReceiptData.getGasPrice())
                          .gasUsed(transactionReceiptData.getGasUsed())
                          .key(transactionReceiptData.getKey())
                          .input(transactionReceiptData.getInput())
                          .logs(logs)
                          .logsBloom(transactionReceiptData.getLogsBloom())
                          .nonce(transactionReceiptData.getNonce())
                          .senderTxHash(transactionReceiptData.getSenderTxHash())
                          .signatures(signatures)
                          .status(transactionReceiptData.getStatus())
                          .to(transactionReceiptData.getTo())
                          .transactionIndex(transactionReceiptData.getTransactionIndex())
                          .transactionHash(transactionReceiptData.getTransactionHash())
                          .txError(transactionReceiptData.getTxError())
                          .type(transactionReceiptData.getType())
                          .typeInt(transactionReceiptData.getTypeInt())
                          .value(transactionReceiptData.getValue())
                          .build()
                );
            }
        }

        // error 생성
        ResGetBlockReceiptDTO.Error error = null;
        if(blockTransactionReceipts.getError() != null) {
            error = ResGetBlockReceiptDTO.Error.builder()
                    .code(blockTransactionReceipts.getError().getCode())
                    .message(blockTransactionReceipts.getError().getMessage())
                    .data(blockTransactionReceipts.getError().getData())
                    .build();
        }

        // response 생성
        ResGetBlockReceiptDTO resGetBlockReceiptDTO = ResGetBlockReceiptDTO.builder()
                .id(blockTransactionReceipts.getId())
                .jsonrpc(blockTransactionReceipts.getJsonrpc())
                .results(results)
                .error(error)
                .rawResponse(blockTransactionReceipts.getRawResponse())
                .build();

        return resGetBlockReceiptDTO;
    }

    @Override
    public ResGetLatestBlockNumberDTO getLatestBlockNumber() {

        long blockNumber = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockNumber().send();
            log.info("quantity : {}", CommonConverter.objectToString(quantity));

            // blockNumber = hexToLong(quantity.getResult());
            blockNumber = quantity.getValue().longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetLatestBlockNumberDTO(blockNumber);
    }

    @Override
    public ResGetBlockDTO getBlockByNumber(Long bockNumber) {

        ResGetBlockDTO resGetBlockDTO = null;

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByNumber(bockNumber).send();
            log.info("block : {}", CommonConverter.objectToString(block));

            resGetBlockDTO = getBlock(block);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetBlockDTO;
    }

    @Override
    public ResGetBlockDTO getBlockByHash(String blockHash) {

        ResGetBlockDTO resGetBlockDTO = null;

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByHash(blockHash).send();
            log.info("block : {}", CommonConverter.objectToString(block));

            resGetBlockDTO = getBlock(block);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetBlockDTO;
    }

    @Override
    public ResGetBlockReceiptDTO getBlockReceiptByHash(String blockHash) {

        ResGetBlockReceiptDTO resGetBlockReceiptDTO = null;

        try {
            BlockTransactionReceipts blockTransactionReceipts = caverExtKAS.rpc.klay.getBlockReceipts(blockHash).send();
            log.info("blockTransactionReceipts : {}", CommonConverter.objectToString(blockTransactionReceipts));

            resGetBlockReceiptDTO = getBlockReceipt(blockTransactionReceipts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetBlockReceiptDTO;
    }

    @Override
    public ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByNumber(Long blockNumber) {

        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = null;

        try {
            BlockWithConsensusInfo blockWithConsensusInfo = caverExtKAS.rpc.klay.getBlockWithConsensusInfoByNumber(blockNumber).send();
            log.info("blockWithConsensusInfo : {}", CommonConverter.objectToString(blockWithConsensusInfo));

            resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfo(blockWithConsensusInfo);
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
            log.info("blockWithConsensusInfo : {}", CommonConverter.objectToString(blockWithConsensusInfo));

            resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfo(blockWithConsensusInfo);
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
            log.info("quantity : {}", CommonConverter.objectToString(quantity));

            // transactionCount = hexToLong(quantity.getResult());
            transactionCount = quantity.getValue().longValue();
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
            log.info("quantity : {}", CommonConverter.objectToString(quantity));

            // transactionCount = hexToLong(quantity.getResult());
            transactionCount = quantity.getValue().longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockTransactionCountDTO(transactionCount);
    }

    @Override
    @Transactional
    public ResCreateBlockDTO createBlockWithTransactionByNumber(ReqCreateBlockByNumberDTO reqCreateBlockByNumberDTO) {

        // 블록 + 트랜잭션 해시 정보 조회
        ResGetBlockDTO resGetBlockDTO = getBlockByNumber(reqCreateBlockByNumberDTO.getBlockNumber());

        // 에러 체크
        if(resGetBlockDTO.getError() != null) {

            ReqBlockErrorDTO.Error error = ReqBlockErrorDTO.Error.builder()
                    .code(resGetBlockDTO.getError().getCode())
                    .message(resGetBlockDTO.getError().getMessage())
                    .data(resGetBlockDTO.getError().getData())
                    .build();

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockDTO.getId())
                    .jsonrpc(resGetBlockDTO.getJsonrpc())
                    .error(error)
                    .kasRequestType(KASRequestType.GET_BLOCK_BY_NUMBER)
                    .rawResponse(resGetBlockDTO.getRawResponse())
                    .build();

            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 블록 consensus 정보 조회 (proposer, committee)
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByNumber(reqCreateBlockByNumberDTO.getBlockNumber());

        // 에러 체크
        if(resGetBlockWithConsensusInfoDTO.getError() != null) {

            ReqBlockErrorDTO.Error error = ReqBlockErrorDTO.Error.builder()
                    .code(resGetBlockWithConsensusInfoDTO.getError().getCode())
                    .message(resGetBlockWithConsensusInfoDTO.getError().getMessage())
                    .data(resGetBlockWithConsensusInfoDTO.getError().getData())
                    .build();

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockWithConsensusInfoDTO.getId())
                    .jsonrpc(resGetBlockWithConsensusInfoDTO.getJsonrpc())
                    .error(error)
                    .kasRequestType(KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER)
                    .rawResponse(resGetBlockWithConsensusInfoDTO.getRawResponse())
                    .build();

            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 해당 블록 번호의 트랜잭션 리스트 조회
        ResGetBlockReceiptDTO resGetBlockReceiptDTO = getBlockReceiptByHash(resGetBlockDTO.getResult().getHash());

        // res 해시 리스트 생성
        List<String> transactionHashList = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            transactionHashList.add(result.getTransactionHash());
        }

        // 에러 체크
        if(resGetBlockReceiptDTO.getError() != null) {

            ReqBlockErrorDTO.Error error = ReqBlockErrorDTO.Error.builder()
                    .code(resGetBlockReceiptDTO.getError().getCode())
                    .message(resGetBlockReceiptDTO.getError().getMessage())
                    .data(resGetBlockReceiptDTO.getError().getData())
                    .build();

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockReceiptDTO.getId())
                    .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                    .error(error)
                    .kasRequestType(KASRequestType.GET_BLOCK_RECEIPTS)
                    .rawResponse(resGetBlockReceiptDTO.getRawResponse())
                    .build();

            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 블록 정보 저장 (블록 + consensus 정보)
        ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
                .jsonrpc(resGetBlockDTO.getJsonrpc())
                .resultByGetBlock(resGetBlockDTO.getResult())
                .resultByGetBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO.getResult())
                .build();
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));


        // 트랜잭션 정보 저장
        ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
                .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                .results(resGetBlockReceiptDTO.getResults())
                .build();
        io.dkargo.bcexplorer.domain.entity.Transaction transaction = transactionRepository.save(TransactionByKASConverter.of(reqTransactionDTO));

        return new ResCreateBlockDTO(CommonConverter.hexToLong(block.getResult().getNumber()), block.getResult().getHash(), transactionHashList);
    }

    @Override
    @Transactional
    public ResCreateBlockDTO createBlockWithTransactionByHash(ReqCreateBlockByHashDTO reqCreateBlockByHashDTO) {

        // 블록 + 트랜잭션 해시 정보 조회
        ResGetBlockDTO resGetBlockDTO = getBlockByHash(reqCreateBlockByHashDTO.getBlockHash());

        // 에러 체크
        if(resGetBlockDTO.getError() != null) {

            ReqBlockErrorDTO.Error error = ReqBlockErrorDTO.Error.builder()
                    .code(resGetBlockDTO.getError().getCode())
                    .message(resGetBlockDTO.getError().getMessage())
                    .data(resGetBlockDTO.getError().getData())
                    .build();

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockDTO.getId())
                    .jsonrpc(resGetBlockDTO.getJsonrpc())
                    .error(error)
                    .kasRequestType(KASRequestType.GET_BLOCK_BY_NUMBER)
                    .rawResponse(resGetBlockDTO.getRawResponse())
                    .build();

            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 블록 consensus 정보 조회 (proposer, committee)
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByHash(reqCreateBlockByHashDTO.getBlockHash());

        // 에러 체크
        if(resGetBlockWithConsensusInfoDTO.getError() != null) {

            ReqBlockErrorDTO.Error error = ReqBlockErrorDTO.Error.builder()
                    .code(resGetBlockWithConsensusInfoDTO.getError().getCode())
                    .message(resGetBlockWithConsensusInfoDTO.getError().getMessage())
                    .data(resGetBlockWithConsensusInfoDTO.getError().getData())
                    .build();

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockWithConsensusInfoDTO.getId())
                    .jsonrpc(resGetBlockWithConsensusInfoDTO.getJsonrpc())
                    .error(error)
                    .kasRequestType(KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER)
                    .rawResponse(resGetBlockWithConsensusInfoDTO.getRawResponse())
                    .build();

            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 해당 블록 번호의 트랜잭션 리스트 조회
        ResGetBlockReceiptDTO resGetBlockReceiptDTO = getBlockReceiptByHash(resGetBlockDTO.getResult().getHash());

        List<String> transactionHashList = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            transactionHashList.add(result.getTransactionHash());
        }

        // 에러 체크
        if(resGetBlockReceiptDTO.getError() != null) {

            ReqBlockErrorDTO.Error error = ReqBlockErrorDTO.Error.builder()
                    .code(resGetBlockReceiptDTO.getError().getCode())
                    .message(resGetBlockReceiptDTO.getError().getMessage())
                    .data(resGetBlockReceiptDTO.getError().getData())
                    .build();

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockReceiptDTO.getId())
                    .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                    .error(error)
                    .kasRequestType(KASRequestType.GET_BLOCK_RECEIPTS)
                    .rawResponse(resGetBlockReceiptDTO.getRawResponse())
                    .build();

            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 블록 정보 저장 (블록 + consensus 정보)
        ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
                .jsonrpc(resGetBlockDTO.getJsonrpc())
                .resultByGetBlock(resGetBlockDTO.getResult())
                .resultByGetBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO.getResult())
                .build();
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));


        // 트랜잭션 정보 저장
        ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
                .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                .results(resGetBlockReceiptDTO.getResults())
                .build();
        io.dkargo.bcexplorer.domain.entity.Transaction transaction = transactionRepository.save(TransactionByKASConverter.of(reqTransactionDTO));

        return new ResCreateBlockDTO(CommonConverter.hexToLong(block.getResult().getNumber()), block.getResult().getHash(), transactionHashList);
    }
}
