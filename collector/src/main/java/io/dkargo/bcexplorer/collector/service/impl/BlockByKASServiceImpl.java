package io.dkargo.bcexplorer.collector.service.impl;

import com.klaytn.caver.methods.response.*;
import com.klaytn.caver.wallet.keyring.SignatureData;
import io.dkargo.bcexplorer.collector.service.AccountByKASService;
import io.dkargo.bcexplorer.collector.service.BcCommErrorHandlingByKASService;
import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.collector.service.converter.*;
import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.core.error.DkargoException;
import io.dkargo.bcexplorer.core.error.ErrorCodeEnum;
import io.dkargo.bcexplorer.core.type.KASRequestType;
import io.dkargo.bcexplorer.domain.entity.Eoa;
import io.dkargo.bcexplorer.domain.entity.Sca;
import io.dkargo.bcexplorer.domain.repository.*;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqEoaDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqScaDTO;
import io.dkargo.bcexplorer.dto.domain.kas.block.request.ReqBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoByHashDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoByNumberDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;
import io.dkargo.bcexplorer.dto.domain.kas.transaction.request.ReqTransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

import java.lang.Boolean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockByKASServiceImpl implements BlockByKASService {

    private final CaverExtKAS caverExtKAS;

    private final AccountByKASService accountByKASService;
    private final BcCommErrorHandlingByKASService bcCommErrorHandlingByKASService;

    private final BlockRepository blockRepository;
    private final TransactionRepository transactionRepository;
    private final EoaRepository eoaRepository;
    private final ScaRepository scaRepository;

    private final BcCommErrorRepository bcCommErrorRepository;

    @Value("${block-info.select-block}")
    private Boolean selectBlock;

    @Value("${block-info.select-block-number}")
    private Long selectBlockNumber;

    public static ResGetBlockDTO getBlock(Block block) {

        ResGetBlockDTO.Result result = null;
        if(block.getResult() != null) {

            // result -> transactions ??????
            List<ResGetBlockDTO.Result.Transaction> transactions = new ArrayList<>();
            if(block.getResult().getTransactions() != null) {

                /**
                 * TODO 1.result -> transactions ?????? (..., feePayerSignatures, signatures)
                 * TODO 2.transactions ??? add
                 *
                 * ?????? TODO??? ??? ?????? ??????. Block ???????????? transaction??? ?????? ????????? ?????? ?????????.
                 */
            }

            // result ??????
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

        // error ??????
        ResGetBlockDTO.Error error = null;
        if(block.getError() != null) {

            error = ResGetBlockDTO.Error.builder()
                    .code(block.getError().getCode())
                    .message(block.getError().getMessage())
                    .data(block.getError().getData())
                    .build();
        }

        // response ??????
        ResGetBlockDTO resGetBlockDTO = ResGetBlockDTO.builder()
                .id(block.getId())
                .jsonrpc(block.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(block.getRawResponse())
                .build();

        return resGetBlockDTO;
    }

    // block with consensus ?????? ?????? ??????
    public static ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfo(BlockWithConsensusInfo blockWithConsensusInfo) {

        ResGetBlockWithConsensusInfoDTO.Result result = null;
        if(blockWithConsensusInfo.getResult() != null) {

            // result -> transactions ??????
            List<ResGetBlockWithConsensusInfoDTO.Result.Transaction> transactions = new ArrayList<>();
            if(blockWithConsensusInfo.getResult().getTransactions() != null) {
                for(Transaction.TransactionData transactionData : blockWithConsensusInfo.getResult().getTransactions()) {

                    // result -> transactions -> feePayerSignatures ??????
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

                    // result -> transactions -> signatures ??????
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

            // result ??????
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

        // error ??????
        ResGetBlockWithConsensusInfoDTO.Error error = null;
        if (blockWithConsensusInfo.getError() != null) {
            error = ResGetBlockWithConsensusInfoDTO.Error.builder()
                    .code(blockWithConsensusInfo.getError().getCode())
                    .message(blockWithConsensusInfo.getError().getMessage())
                    .data(blockWithConsensusInfo.getError().getData())
                    .build();
        }

        // response ??????
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = ResGetBlockWithConsensusInfoDTO.builder()
                .id(blockWithConsensusInfo.getId())
                .jsonrpc(blockWithConsensusInfo.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(blockWithConsensusInfo.getRawResponse())
                .build();

        return resGetBlockWithConsensusInfoDTO;
    }

    // block receipt ?????? ?????? ??????
    public static ResGetBlockReceiptDTO getBlockReceipt(BlockTransactionReceipts blockTransactionReceipts) {

        List<ResGetBlockReceiptDTO.Result> results = new ArrayList<>();
        if(blockTransactionReceipts.getResult() != null) {

            // result ?????? ?????? ??????
            for(TransactionReceipt.TransactionReceiptData transactionReceiptData : blockTransactionReceipts.getResult()) {

                // result -> feePayerSignatures ??????
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

                // result -> logs ??????
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

                // result -> signatures ??????
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

                // gasPrice / txFee / amount ??? ??????
                Float gasPriceToFloat = null;
                String gasPriceToString = "0";
                if(transactionReceiptData.getGasPrice() != null) {
                    gasPriceToFloat = CommonConverter.hexToKlayUnit(transactionReceiptData.getGasPrice()); // gasPrice(hex) ?????? Klay ????????? ?????? ??????
                    gasPriceToString = CommonConverter.floatToFormatString(gasPriceToFloat);
                    log.info("gasPriceToString : {}", CommonConverter.floatToFormatString(gasPriceToFloat));
                } else { // ????????? ???????????? ????????? TX TYPE??? Ethereum Dynamic Fee ?????? ??????????????? gasPrice ?????? 0.00000025??? ???????????? ????????? RPC ?????? ??? ???????????? ?????? ?????????
                    gasPriceToFloat = 0.00000025f;
                    gasPriceToString = CommonConverter.floatToFormatString(gasPriceToFloat);
                }

                Float txFee = null;
                String txFeeToString = "0";
                if(gasPriceToFloat != null && transactionReceiptData.getGasUsed() != null) {
                    txFee = gasPriceToFloat * CommonConverter.hexToLong(transactionReceiptData.getGasUsed()); // gasPrice * gasUsed
                    txFeeToString = CommonConverter.floatToFormatString(txFee);
                    log.info("txFeeToString : {}", CommonConverter.floatToFormatString(txFee));
                }

                BigDecimal amount = null;
                String amountToString = "0";
                if(transactionReceiptData.getValue() != null) {
                    amount = CommonConverter.hexToBigDecimal(transactionReceiptData.getValue());
                    amountToString = CommonConverter.bigDecimalToFormatString(amount);
                    log.info("amountToString : {}", CommonConverter.bigDecimalToFormatString(amount));
                }


                // methodSig ??? ?????? (input ?????? "0x" ??? ?????? ??????)
                String methodSig = null;
                if(transactionReceiptData.getInput() != null) {
                    if(transactionReceiptData.getInput().length() >= 10){
                        methodSig = transactionReceiptData.getInput().substring(0,10); // input ??? ????????? ????????? ?????? 10?????? ????????? ?????? ?????????
                        log.info("methodSig : {}", transactionReceiptData.getInput().substring(0,10));
                    } else {
                        methodSig = transactionReceiptData.getInput();
                        log.info("methodSig : {}", transactionReceiptData.getInput());
                    }
                }

                // results ??????
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
                          .gasPriceByFormat(gasPriceToString)
                          .gasUsed(transactionReceiptData.getGasUsed())
                          .txFee(txFeeToString)
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
                          .amount(amountToString)
                          .methodSig(methodSig)
                          .build()
                );
            }
        }

        // error ??????
        ResGetBlockReceiptDTO.Error error = null;
        if(blockTransactionReceipts.getError() != null) {
            error = ResGetBlockReceiptDTO.Error.builder()
                    .code(blockTransactionReceipts.getError().getCode())
                    .message(blockTransactionReceipts.getError().getMessage())
                    .data(blockTransactionReceipts.getError().getData())
                    .build();
        }

        // response ??????
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
    public ResCreateBlockChainInfoDTO createBlockChainInfoByNumber(ReqCreateBlockChainInfoByNumberDTO reqCreateBlockChainInfoByNumberDTO) {

        // ?????? + ???????????? ?????? ?????? ??????
        ResGetBlockDTO resGetBlockDTO = getBlockByNumber(reqCreateBlockChainInfoByNumberDTO.getBlockNumber());

        // ?????? ??????
        if(resGetBlockDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlock(resGetBlockDTO, KASRequestType.GET_BLOCK_BY_NUMBER));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // ?????? consensus ?????? ?????? (proposer, committee)
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByNumber(reqCreateBlockChainInfoByNumberDTO.getBlockNumber());

        // ?????? ??????
        if(resGetBlockWithConsensusInfoDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // ?????? ?????? ????????? ???????????? ????????? ??????
        ResGetBlockReceiptDTO resGetBlockReceiptDTO = getBlockReceiptByHash(resGetBlockDTO.getResult().getHash());

        // ?????? ??????
        if(resGetBlockReceiptDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockReceipt(resGetBlockReceiptDTO, KASRequestType.GET_BLOCK_RECEIPTS));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // totalTxFee(block ??????) ??????
        // transaction ?????? ?????????(response ??????) ??????
        // account(eoa ?????????/sca ?????????) ??????
        Double totalTxFee = 0d;
        List<String> transactionHashList = new ArrayList<>();
        List<Eoa> eoas = new ArrayList<>();
        List<Sca> scas = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            totalTxFee += Double.parseDouble(result.getTxFee());
            transactionHashList.add(result.getTransactionHash());

            // from : ????????? EOA ??? ?????? ------------------------------------------------

            // from ?????? DB ??? ????????????????????? ?????? ??? ????????? ?????? List??? ??????, ????????? ?????? ??????
            Eoa eoaByFrom = eoaRepository.findByResult_address(result.getFrom());

            // ?????? ?????? ?????? ?????? ??????
            ResGetAccountDTO resGetAccountDTOByFrom = accountByKASService.getAccountByAddress(result.getFrom());
            // ?????? ??????
            if(resGetAccountDTOByFrom.getError() != null) {

                bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByFrom, KASRequestType.GET_ACCOUNT_BY_ADDRESS));
                throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
            }

            if(eoaByFrom == null) {
                // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                ReqEoaDTO reqEoaDTOByFrom = ReqEoaDTO.builder()
                        .jsonrpc(resGetAccountDTOByFrom.getJsonrpc())
                        .resultByGetAccount(resGetAccountDTOByFrom.getResult())
                        .build();
                eoas.add(EoaByKASConverter.of(reqEoaDTOByFrom));
            } else {
                // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                eoaByFrom.update(resGetAccountDTOByFrom, 9999D, 9999L);
                eoas.add(eoaByFrom);
            }
            // ------------------------------------------------------------------------

            // to : EOA or SCA or NUll ------------------------------------------------
            if(result.getTo() != null) {

                ResGetAccountDTO resGetAccountDTOByTo = accountByKASService.getAccountByAddress(result.getTo());
                // ?????? ??????
                if(resGetAccountDTOByFrom.getError() != null) {

                    bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByTo, KASRequestType.GET_ACCOUNT_BY_ADDRESS));
                    throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
                }

                switch (resGetAccountDTOByTo.getResult().getAccount().getType().toString()) {

                    case "EOA" :
                        // to ?????? DB ??? ????????????????????? ?????? ??? ????????? ?????? List??? ??????, ????????? ?????? ??????
                        Eoa eoaByTo = eoaRepository.findByResult_address(result.getTo());

                        if(eoaByTo == null) {
                            // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                            ReqEoaDTO reqEoaDTOByTo = ReqEoaDTO.builder()
                                    .jsonrpc(resGetAccountDTOByTo.getJsonrpc())
                                    .resultByGetAccount(resGetAccountDTOByTo.getResult())
                                    .build();
                            eoas.add(EoaByKASConverter.of(reqEoaDTOByTo));
                        } else {
                            // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                            eoaByTo.update(resGetAccountDTOByTo, 9999D, 9999L);
                            eoas.add(eoaByTo);
                        }
                        break;

                    case "SCA" :
                        // to ?????? DB ??? ????????????????????? ?????? ??? ????????? ?????? List??? ??????, ????????? ?????? ??????
                        Sca scaByTo = scaRepository.findByResult_address(result.getTo());

                        if(scaByTo == null) {
                            // ?????? ??? SCA ?????? ?????? ??? SCA ???????????? ??????
                            ReqScaDTO reqScaDTOByTo = ReqScaDTO.builder()
                                    .jsonrpc(resGetAccountDTOByTo.getJsonrpc())
                                    .resultByGetAccount(resGetAccountDTOByTo.getResult())
                                    .build();
                            scas.add(ScaByKASConverter.of(reqScaDTOByTo));
                        } else {
                            // ?????? ??? SCA ?????? ?????? ??? SCA ???????????? ??????
                            scaByTo.update(resGetAccountDTOByTo, 9207D, 9207L);
                            scas.add(scaByTo);
                        }
                        break;

                    default :
                        break;
                }
            } else {

                if(result.getContractAddress() != null) {

                    ResGetAccountDTO resGetAccountDTOByContractAddress = accountByKASService.getAccountByAddress(result.getContractAddress());

                    // ?????? ??????
                    if(resGetAccountDTOByFrom.getError() != null) {

                        bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByContractAddress, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER));
                        throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
                    }

                    // ?????? ??? SCA ?????? ?????? ??? SCA ???????????? ??????
                    ReqScaDTO reqScaDTOByContractAddress = ReqScaDTO.builder()
                            .jsonrpc(resGetAccountDTOByContractAddress.getJsonrpc())
                            .resultByGetAccount(resGetAccountDTOByContractAddress.getResult())
                            .build();
                    scas.add(ScaByKASConverter.of(reqScaDTOByContractAddress));
                } else {
                    log.info("Chain Data Anchoring");
                }
            }
            // ---------------------------------------------------------------------
        }

        // ?????? ?????? ?????? (?????? + consensus ??????)
        ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
                .jsonrpc(resGetBlockDTO.getJsonrpc())
                .resultByGetBlock(resGetBlockDTO.getResult())
                .resultByGetBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO.getResult())
                .totalTxFee(totalTxFee)
                .build();
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));

        // ???????????? ?????? ??????
        List<io.dkargo.bcexplorer.domain.entity.Transaction> transactions = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
                    .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                    .result(result)
                    .build();
            transactions.add(TransactionByKASConverter.of(reqTransactionDTO));
        }

        transactionRepository.saveAll(transactions);

        // ??????(EOA/SCA) ?????? ??????
        if(eoas.size() > 0) {
            eoaRepository.saveAll(eoas);
        }
        if(scas.size() > 0) {
            scaRepository.saveAll(scas);
        }

        return new ResCreateBlockChainInfoDTO(CommonConverter.hexToLong(block.getResult().getNumber()), block.getResult().getHash(), transactionHashList);
    }

    @Override
    public ResCreateBlockChainInfoDTO createBlockChainInfoByHash(ReqCreateBlockChainInfoByHashDTO reqCreateBlockByHashDTO) {

        // ?????? + ???????????? ?????? ?????? ??????
        ResGetBlockDTO resGetBlockDTO = getBlockByHash(reqCreateBlockByHashDTO.getBlockHash());

        // ?????? ??????
        if(resGetBlockDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlock(resGetBlockDTO, KASRequestType.GET_BLOCK_BY_HASH));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // ?????? consensus ?????? ?????? (proposer, committee)
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByHash(reqCreateBlockByHashDTO.getBlockHash());

        // ?????? ??????
        if(resGetBlockWithConsensusInfoDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_HASH));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // ?????? ????????? ???????????? ????????? ??????
        ResGetBlockReceiptDTO resGetBlockReceiptDTO = getBlockReceiptByHash(resGetBlockDTO.getResult().getHash());

        // ?????? ??????
        if(resGetBlockReceiptDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockReceipt(resGetBlockReceiptDTO, KASRequestType.GET_BLOCK_RECEIPTS));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // totalTxFee(block ??????) ??????
        // transaction ?????? ?????????(response ??????) ??????
        // account(eoa ?????????/sca ?????????) ??????
        Double totalTxFee = 0d;
        List<String> transactionHashList = new ArrayList<>();
        List<Eoa> eoas = new ArrayList<>();
        List<Sca> scas = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            totalTxFee += Double.parseDouble(result.getTxFee());
            transactionHashList.add(result.getTransactionHash());

            // from : ????????? EOA ??? ?????? ------------------------------------------------

            // from ?????? DB ??? ????????????????????? ?????? ??? ????????? ?????? List??? ??????, ????????? ?????? ??????
            Eoa eoaByFrom = eoaRepository.findByResult_address(result.getFrom());

            // ?????? ?????? ?????? ?????? ??????
            ResGetAccountDTO resGetAccountDTOByFrom = accountByKASService.getAccountByAddress(result.getFrom());
            // ?????? ??????
            if(resGetAccountDTOByFrom.getError() != null) {

                bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByFrom, KASRequestType.GET_ACCOUNT_BY_ADDRESS));
                throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
            }

            if(eoaByFrom == null) {
                // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                ReqEoaDTO reqEoaDTOByFrom = ReqEoaDTO.builder()
                        .jsonrpc(resGetAccountDTOByFrom.getJsonrpc())
                        .resultByGetAccount(resGetAccountDTOByFrom.getResult())
                        .build();
                eoas.add(EoaByKASConverter.of(reqEoaDTOByFrom));
            } else {
                // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                eoaByFrom.update(resGetAccountDTOByFrom, 9999D, 9999L);
                eoas.add(eoaByFrom);
            }
            // ------------------------------------------------------------------------

            // to : EOA or SCA or NUll ------------------------------------------------
            if(result.getTo() != null) {

                ResGetAccountDTO resGetAccountDTOByTo = accountByKASService.getAccountByAddress(result.getTo());
                // ?????? ??????
                if(resGetAccountDTOByFrom.getError() != null) {

                    bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByTo, KASRequestType.GET_ACCOUNT_BY_ADDRESS));
                    throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
                }

                switch (resGetAccountDTOByTo.getResult().getAccount().getType().toString()) {

                    case "EOA" :
                        // to ?????? DB ??? ????????????????????? ?????? ??? ????????? ?????? List??? ??????, ????????? ?????? ??????
                        Eoa eoaByTo = eoaRepository.findByResult_address(result.getTo());

                        if(eoaByTo == null) {
                            // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                            ReqEoaDTO reqEoaDTOByTo = ReqEoaDTO.builder()
                                    .jsonrpc(resGetAccountDTOByTo.getJsonrpc())
                                    .resultByGetAccount(resGetAccountDTOByTo.getResult())
                                    .build();
                            eoas.add(EoaByKASConverter.of(reqEoaDTOByTo));
                        } else {
                            // ?????? ??? EOA ?????? ?????? ??? EOA ???????????? ??????
                            eoaByTo.update(resGetAccountDTOByTo, 9999D, 9999L);
                            eoas.add(eoaByTo);
                        }
                        break;

                    case "SCA" :
                        // to ?????? DB ??? ????????????????????? ?????? ??? ????????? ?????? List??? ??????, ????????? ?????? ??????
                        Sca scaByTo = scaRepository.findByResult_address(result.getTo());

                        if(scaByTo == null) {
                            // ?????? ??? SCA ?????? ?????? ??? SCA ???????????? ??????
                            ReqScaDTO reqScaDTOByTo = ReqScaDTO.builder()
                                    .jsonrpc(resGetAccountDTOByTo.getJsonrpc())
                                    .resultByGetAccount(resGetAccountDTOByTo.getResult())
                                    .build();
                            scas.add(ScaByKASConverter.of(reqScaDTOByTo));
                        } else {
                            // ?????? ??? SCA ?????? ?????? ??? SCA ???????????? ??????
                            scaByTo.update(resGetAccountDTOByTo, 9207D, 9207L);
                            scas.add(scaByTo);
                        }
                        break;

                    default :
                        break;
                }
            } else {

                if(result.getContractAddress() != null) {

                    ResGetAccountDTO resGetAccountDTOByContractAddress = accountByKASService.getAccountByAddress(result.getContractAddress());

                    // ?????? ??????
                    if(resGetAccountDTOByFrom.getError() != null) {

                        bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByContractAddress, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER));
                        throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
                    }

                    // ?????? ??? SCA ?????? ?????? ??? SCA ???????????? ??????
                    ReqScaDTO reqScaDTOByContractAddress = ReqScaDTO.builder()
                            .jsonrpc(resGetAccountDTOByContractAddress.getJsonrpc())
                            .resultByGetAccount(resGetAccountDTOByContractAddress.getResult())
                            .build();
                    scas.add(ScaByKASConverter.of(reqScaDTOByContractAddress));
                } else {
                    log.info("Chain Data Anchoring");
                }
            }
            // ---------------------------------------------------------------------
        }

        // ?????? ?????? ?????? (?????? + consensus ??????)
        ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
                .jsonrpc(resGetBlockDTO.getJsonrpc())
                .resultByGetBlock(resGetBlockDTO.getResult())
                .resultByGetBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO.getResult())
                .totalTxFee(totalTxFee)
                .build();
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));


        // ???????????? ?????? ??????
        List<io.dkargo.bcexplorer.domain.entity.Transaction> transactions = new ArrayList<>();
        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
                    .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                    .result(result)
                    .build();
            transactions.add(TransactionByKASConverter.of(reqTransactionDTO));
        }

        transactionRepository.saveAll(transactions);

        // ??????(EOA/SCA) ?????? ??????
        if(eoas.size() > 0) {
            eoaRepository.saveAll(eoas);
        }
        if(scas.size() > 0) {
            scaRepository.saveAll(scas);
        }

        return new ResCreateBlockChainInfoDTO(CommonConverter.hexToLong(block.getResult().getNumber()), block.getResult().getHash(), transactionHashList);
    }

    @Override
    public ResCreateBlockChainInfoDTO createBlockChainInfoByScheduler() {

        // DB ????????? ?????? ?????? ??? ?????? ????????? ?????? ???(??????) ?????? ?????? ??????
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.findTop1ByOrderByResult_NumberDesc();
        log.info("latest blockNumber : {}", CommonConverter.hexToLong(block.getResult().getNumber()));

        Long nextBlockNumber;
        ReqCreateBlockChainInfoByNumberDTO reqCreateBlockChainInfoByNumberDTO;
        ResCreateBlockChainInfoDTO resCreateBlockChainInfoDTO;

        // DB??? ?????? ?????? ????????? ?????? ??? - ???????????? ????????? ?????? ???????????? ??????
        if(block != null) {

            nextBlockNumber = CommonConverter.hexToLong(block.getResult().getNumber()) + 1;
            log.info("nextBlockNumber(createBlockNumber) : {}", nextBlockNumber);

            reqCreateBlockChainInfoByNumberDTO = ReqCreateBlockChainInfoByNumberDTO.builder()
                    .blockNumber(nextBlockNumber)
                    .build();

            resCreateBlockChainInfoDTO = createBlockChainInfoByNumber(reqCreateBlockChainInfoByNumberDTO);
        }
        // DB??? ?????? ?????? ????????? ?????? ???
        else {

            // ????????? ?????? ???????????? ?????? ??? ??? - ????????? ???????????? ??????
            if(selectBlock) {

                nextBlockNumber = selectBlockNumber;
                log.info("nextBlockNumber : {}", nextBlockNumber);

                reqCreateBlockChainInfoByNumberDTO = ReqCreateBlockChainInfoByNumberDTO.builder()
                        .blockNumber(nextBlockNumber)
                        .build();

                resCreateBlockChainInfoDTO = createBlockChainInfoByNumber(reqCreateBlockChainInfoByNumberDTO);
            }
            // ????????? ?????? ???????????? ???????????? ?????? ??? - ?????? ?????? ???????????? ??????
            else {

                ResGetLatestBlockNumberDTO resGetLatestBlockNumberDTO = getLatestBlockNumber();
                nextBlockNumber = resGetLatestBlockNumberDTO.getBlockNumber();
                log.info("nextBlockNumber :{}", nextBlockNumber);

                reqCreateBlockChainInfoByNumberDTO = ReqCreateBlockChainInfoByNumberDTO.builder()
                        .blockNumber(nextBlockNumber)
                        .build();

                resCreateBlockChainInfoDTO = createBlockChainInfoByNumber(reqCreateBlockChainInfoByNumberDTO);
            }
        }

        return resCreateBlockChainInfoDTO;
    }
}
