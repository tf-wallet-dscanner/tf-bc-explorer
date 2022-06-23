package io.dkargo.bcexplorer.collector.service.impl;

import com.klaytn.caver.methods.response.*;
import com.klaytn.caver.wallet.keyring.SignatureData;
import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import io.dkargo.bcexplorer.collector.service.converter.BlockByKASConverter;
import io.dkargo.bcexplorer.collector.service.converter.BlockErrorByKASConverter;
import io.dkargo.bcexplorer.collector.service.converter.CommonConverter;
import io.dkargo.bcexplorer.core.error.DkargoException;
import io.dkargo.bcexplorer.core.error.ErrorCodeEnum;
import io.dkargo.bcexplorer.domain.entity.BlockError;
import io.dkargo.bcexplorer.domain.repository.BlockErrorRepository;
import io.dkargo.bcexplorer.domain.repository.BlockRepository;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqBlockErrorDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByHashDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByNumberDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;
import io.dkargo.bcexplorer.dto.collector.kas.transaction.response.ResGetTransactionReceiptByHashDTO;
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

    private final TransactionByKASService transactionByKASService;

    private final BlockRepository blockRepository;
    private final BlockErrorRepository blockErrorRepository;

    // block with consensus 정보 조회 포멧
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

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByNumber(bockNumber).send();
            log.info("block : {}", CommonConverter.objectToString(block));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockDTO(bockNumber);
    }

    @Override
    public void getBlockByHash(String blockHash) {

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByHash(blockHash).send();
            log.info("block : {}", CommonConverter.objectToString(block));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBlockReceiptByHash(String blockHash) {

        try {
            BlockTransactionReceipts blockTransactionReceipts = caverExtKAS.rpc.klay.getBlockReceipts(blockHash).send();
            log.info("blockTransactionReceipts : {}", CommonConverter.objectToString(blockTransactionReceipts));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        // 블록 정보 조회
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByNumber(reqCreateBlockByNumberDTO.getBlockNumber());

        // 에러 확인
        if(resGetBlockWithConsensusInfoDTO.getError() != null) {

            ReqBlockErrorDTO reqBlockErrorDTO = ReqBlockErrorDTO.builder()
                    .id(resGetBlockWithConsensusInfoDTO.getId())
                    .jsonrpc(resGetBlockWithConsensusInfoDTO.getJsonrpc())
                    .code(resGetBlockWithConsensusInfoDTO.getError().getCode())
                    .message(resGetBlockWithConsensusInfoDTO.getError().getMessage())
                    .data(resGetBlockWithConsensusInfoDTO.getError().getData())
                    .rawResponse(resGetBlockWithConsensusInfoDTO.getRawResponse())
                    .build();
            blockErrorRepository.save(BlockErrorByKASConverter.of(reqBlockErrorDTO));

            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // 트랜잭션 개수 만큼 트랜잭션 정보 조회
        List<ResGetTransactionReceiptByHashDTO> resGetTransactionReceiptByHashDTOS = new ArrayList<>();
        for(ResGetBlockWithConsensusInfoDTO.Result.Transactions transactions : resGetBlockWithConsensusInfoDTO.getResult().getTransactions()) {

            // 트랜잭션 정보 조회
            ResGetTransactionReceiptByHashDTO resGetTransactionReceiptByHashDTO = transactionByKASService.getTransactionReceiptByHash(transactions.getSenderTxHash());

            if(resGetTransactionReceiptByHashDTO.getError() != null) {

                /**
                 * TODO : transactionErrorRepository 저장 후 에러 처리
                 */
            }

            resGetTransactionReceiptByHashDTOS.add(
                    ResGetTransactionReceiptByHashDTO.builder()
                            .id(resGetTransactionReceiptByHashDTO.getId())
                            .jsonrpc(resGetTransactionReceiptByHashDTO.getJsonrpc())
                            .result(resGetTransactionReceiptByHashDTO.getResult())
                            .error(resGetTransactionReceiptByHashDTO.getError())
                            .rawResponse(resGetTransactionReceiptByHashDTO.getRawResponse())
                            .build()
            );
        }

        // 블록 생성
        ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
                .id(resGetBlockWithConsensusInfoDTO.getId())
                .jsonrpc(resGetBlockWithConsensusInfoDTO.getJsonrpc())
                .error(resGetBlockWithConsensusInfoDTO.getError())
                .rawResponse(resGetBlockWithConsensusInfoDTO.getRawResponse())
                .blockScore(resGetBlockWithConsensusInfoDTO.getResult().getBlockScore())
                .totalBlockScore(resGetBlockWithConsensusInfoDTO.getResult().getTotalBlockScore())
                .committee(resGetBlockWithConsensusInfoDTO.getResult().getCommittee())
                .gasLimit(resGetBlockWithConsensusInfoDTO.getResult().getGasLimit())
                .gasUsed(resGetBlockWithConsensusInfoDTO.getResult().getGasUsed())
                .hash(resGetBlockWithConsensusInfoDTO.getResult().getHash())
                .miner(resGetBlockWithConsensusInfoDTO.getResult().getMiner())
                .nonce(resGetBlockWithConsensusInfoDTO.getResult().getNonce())
                .number(resGetBlockWithConsensusInfoDTO.getResult().getNumber())
                .parentBlockHash(resGetBlockWithConsensusInfoDTO.getResult().getParentBlockHash())
                .proposer(resGetBlockWithConsensusInfoDTO.getResult().getProposer())
                .receiptsRoot(resGetBlockWithConsensusInfoDTO.getResult().getReceiptsRoot())
                .size(resGetBlockWithConsensusInfoDTO.getResult().getSize())
                .stateRoot(resGetBlockWithConsensusInfoDTO.getResult().getStateRoot())
                .timestamp(resGetBlockWithConsensusInfoDTO.getResult().getTimestamp())
                .timestampFoS(resGetBlockWithConsensusInfoDTO.getResult().getTimestampFoS())
                .transactionsRoot(resGetBlockWithConsensusInfoDTO.getResult().getTransactionsRoot())
                .transactionCount((long) resGetBlockWithConsensusInfoDTO.getResult().getTransactions().size())
                .transactions(resGetTransactionReceiptByHashDTOS)
                .build();
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));

        /**
         * TODO : 트랜잭션 생성 로직 추가
         */

        return new ResCreateBlockDTO(CommonConverter.hexToLong(block.getNumber()), resGetBlockWithConsensusInfoDTO.getResult().getHash());
    }

    @Override
    @Transactional
    public ResCreateBlockDTO createBlockWithTransactionByHash(ReqCreateBlockByHashDTO reqCreateBlockByHashDTO) {

        // 블록 정보 조회
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByHash(reqCreateBlockByHashDTO.getBlockHash());

        return new ResCreateBlockDTO(111L, resGetBlockWithConsensusInfoDTO.getResult().getHash());
    }
}
