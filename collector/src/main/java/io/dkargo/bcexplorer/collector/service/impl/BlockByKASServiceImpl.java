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
import io.dkargo.bcexplorer.domain.entity.ManagedAccount;
import io.dkargo.bcexplorer.domain.entity.Sca;
import io.dkargo.bcexplorer.domain.repository.*;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoBySchedulerDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqEoaDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqScaDTO;
import io.dkargo.bcexplorer.dto.domain.kas.block.request.ReqBlockDTO;
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

    private final ManagedAccountRepository managedAccountRepository;
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

            // result -> transactions 생성
            List<ResGetBlockDTO.Result.Transaction> transactions = new ArrayList<>();
            if(block.getResult().getTransactions() != null) {

                /**
                 * TODO 1.result -> transactions 생성 (..., feePayerSignatures, signatures)
                 * TODO 2.transactions 에 add
                 *
                 * 해당 TODO를 할 필요 없음. Block 정보에는 transaction에 대한 정보를 넣지 않는다.
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

                // gasPrice / txFee / amount 값 생성
                Float gasPriceToFloat = null;
                String gasPriceToString = "0";
                if(transactionReceiptData.getGasPrice() != null) {
                    gasPriceToFloat = CommonConverter.hexToKlayUnit(transactionReceiptData.getGasPrice()); // gasPrice(hex) 값을 Klay 단위에 맞게 변경
                    gasPriceToString = CommonConverter.floatToFormatString(gasPriceToFloat);
                    log.info("gasPriceToString : {}", CommonConverter.floatToFormatString(gasPriceToFloat));
                } else { // 위에와 계산식은 같지만 TX TYPE이 Ethereum Dynamic Fee 경우 화면에서는 gasPrice 값이 0.00000025로 고정되어 있지만 RPC 조회 시 나타나지 않음 때문에
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


                // methodSig 값 생성 (input 값이 "0x" 일 수도 있음)
                String methodSig = null;
                if(transactionReceiptData.getInput() != null) {
                    if(transactionReceiptData.getInput().length() >= 10){
                        methodSig = transactionReceiptData.getInput().substring(0,10); // input 의 첫번째 자릿수 부터 10번째 자릿수 까지 문자열
                        log.info("methodSig : {}", transactionReceiptData.getInput().substring(0,10));
                    } else {
                        methodSig = transactionReceiptData.getInput();
                        log.info("methodSig : {}", transactionReceiptData.getInput());
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
    public ResCreateBlockChainInfoDTO createBlockChainInfoByNumber(ReqCreateBlockChainInfoByNumberDTO reqCreateBlockChainInfoByNumberDTO) {

        // 존재 기존에 있는 블록인지 확인
        io.dkargo.bcexplorer.domain.entity.Block existenceBlock = blockRepository.findByResult_NumberAndServiceCode(CommonConverter.longToHex(reqCreateBlockChainInfoByNumberDTO.getBlockNumber()), reqCreateBlockChainInfoByNumberDTO.getServiceCode());
        if(existenceBlock != null) {
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "해당 블록 정보가 이미 존재합니다.");
        }

        // --------------------------------------------------------------------------------------
        // -------- 특정 서비스에 상관없이 아래 노드 통신 로직은 무조건 통일!! (일단 블록을 까봐야 안다!!) ---------
        // --------------------------------------------------------------------------------------

        // 블록 + 트랜잭션 해시 정보 조회 (kas는 private 노드에서 불가능 하기때문에, public 노드를 바라보고 kas 통신으로 고정)
        ResGetBlockDTO resGetBlockDTO = getBlockByNumber(reqCreateBlockChainInfoByNumberDTO.getBlockNumber());

        // 에러 체크
        if(resGetBlockDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlock(resGetBlockDTO, KASRequestType.GET_BLOCK_BY_NUMBER, reqCreateBlockChainInfoByNumberDTO.getServiceCode()));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "KAS[getBlockByNumber] 통신 에러가 발생하였습니다.");
        }

        // 블록 consensus 정보 조회 (proposer, committee) (kas는 private 노드에서 불가능 하기때문에, public 노드를 바라보고 kas 통신으로 고정)
        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByNumber(reqCreateBlockChainInfoByNumberDTO.getBlockNumber());

        // 에러 체크
        if(resGetBlockWithConsensusInfoDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER, reqCreateBlockChainInfoByNumberDTO.getServiceCode()));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "KAS[getBlockWithConsensusInfo] 통신 에러가 발생하였습니다.");
        }

        // 해당 블록 번호의 트랜잭션 리스트 조회 - 해당 API를 요청하려면 번호대신 hash값이 필요함 (kas는 private 노드에서 불가능 하기때문에, public 노드를 바라보고 kas 통신으로 고정)
        ResGetBlockReceiptDTO resGetBlockReceiptDTO = getBlockReceiptByHash(resGetBlockDTO.getResult().getHash());

        // 에러 체크
        if(resGetBlockReceiptDTO.getError() != null) {

            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockReceipt(resGetBlockReceiptDTO, KASRequestType.GET_BLOCK_RECEIPTS, reqCreateBlockChainInfoByNumberDTO.getServiceCode()));
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "KAS[getBlockReceipt] 통신 에러가 발생하였습니다.");
        }

        // --------------------------------------------------------------------------------------
        // --------------------------------------------------------------------------------------
        // --------------------------------------------------------------------------------------

        // 관리 할 data가 all or filter 확인 - serviceCode 첫번째 문자를 통해 확인 가능
        List<String> addressesByFilter = new ArrayList<>();
        switch (reqCreateBlockChainInfoByNumberDTO.getServiceCode().substring(0, 1)) {

            case "a" :
                log.info("all service!!");
                break;

            case "f" :
                log.info("filter service!!");
                List<ManagedAccount> managedAccounts = managedAccountRepository.findAllByServiceCode(reqCreateBlockChainInfoByNumberDTO.getServiceCode());

                if(managedAccounts.size() > 0) {

                    for(ManagedAccount managedAccount : managedAccounts) {

                        addressesByFilter.add(managedAccount.getAddress());
                    }
                }
                break;

            default:
                break;
        }

        // 저장 할 트랜잭션 리스트
        List<io.dkargo.bcexplorer.domain.entity.Transaction> transactions = new ArrayList<>();

        // totalTxFee(block 정보) 생성
        // transaction 해시 리스트(response 정보) 생성
        // account(eoa 리스트/sca 리스트) 생성 하기위한 리스트 생성 (중복제거)
        Double totalTxFee = 0d;
        List<String> transactionHashList = new ArrayList<>();
        List<Eoa> eoas = new ArrayList<>();
        List<Sca> scas = new ArrayList<>();

        List<String> addressesByAll = new ArrayList<>();

        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {

            // account를 저장을 위해 중복을 제외하여, 모든 address( EOA/SCA : from,to,contractAddress) 리스트 생성)
            if(!addressesByAll.contains(result.getFrom())){
                addressesByAll.add(result.getFrom());
            }
            if(result.getTo() != null && !addressesByAll.contains(result.getTo())){
                addressesByAll.add(result.getTo());
            }
            if(result.getContractAddress() != null && !addressesByAll.contains(result.getContractAddress())){
                addressesByAll.add(result.getContractAddress());
            }

            totalTxFee += Double.parseDouble(result.getTxFee()); //이건 블록안에있는 전체 트랜잭션에 대한 계산값이기 때문에 계산식 동일

            // 응답 값과 저장 할 트랜잭션 리스트 생성

            // 서비스가 ALL(전체) 이면
            if(reqCreateBlockChainInfoByNumberDTO.getServiceCode().substring(0, 1).equals("a")){
                // 저장된 트랜잭션 리스트에 대한 응답
                transactionHashList.add(result.getTransactionHash());

                // 생성 할 트랜잭션 리스트에 추가
                ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
                        .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                        .result(result)
                        .serviceCode(reqCreateBlockChainInfoByNumberDTO.getServiceCode())
                        .build();
                transactions.add(TransactionByKASConverter.of(reqTransactionDTO));

            }
            // 서비스 Filter(특정) 이면
            else if(reqCreateBlockChainInfoByNumberDTO.getServiceCode().substring(0, 1).equals("f")){
                // 해당 주소가 service 관리 주소에 포함되는지 확인
                if (addressesByFilter.contains(result.getFrom()) || addressesByFilter.contains(result.getTo()) || addressesByFilter.contains(result.getContractAddress())){
                    // 저장된 트랜잭션 리스트에 대한 응답
                    transactionHashList.add(result.getTransactionHash());

                    // 생성 할 트랜잭션 리스트에 추가
                    ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
                            .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                            .result(result)
                            .serviceCode(reqCreateBlockChainInfoByNumberDTO.getServiceCode())
                            .build();
                    transactions.add(TransactionByKASConverter.of(reqTransactionDTO));
                } else {
                    log.info("Address not included!!");
                }
            }
            else{
                throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "serviceCode는 All(a) or Filter(f) 두가지로만 나뉩니다.");
            }
        }

        Boolean validTransaction = false;

        // EOA/SCA 생성 - from은 무조건 EOA만 존재, to는 EOA,SCA,NULL 존재, contractAddress는 SCA만 존재
        for(String addressByAll : addressesByAll) {

            ResGetAccountDTO resGetAccountDTO = null;

            // 서비스가 ALL(전체) 이면
            if(reqCreateBlockChainInfoByNumberDTO.getServiceCode().substring(0, 1).equals("a")){
                // 해당 값의 최신 정보 조회 (kas는 private 노드에서 불가능 하기때문에, public 노드를 바라보고 kas 통신으로 고정)
                resGetAccountDTO = accountByKASService.getAccountByAddress(addressByAll);
            }
            // 서비스 Filter(특정) 이면
            else if(reqCreateBlockChainInfoByNumberDTO.getServiceCode().substring(0, 1).equals("f")){
                // 해당 주소가 service 관리 주소에 포함되는지 확인
                if(addressesByFilter.contains(addressByAll)){
                    // 해당 값의 최신 정보 조회 (kas는 private 노드에서 불가능 하기때문에, public 노드를 바라보고 kas 통신으로 고정)
                    resGetAccountDTO = accountByKASService.getAccountByAddress(addressByAll);
                } else {
                    log.info("Address not included!!");
                }
            }
            else{
                throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "serviceCode는 All(a) or Filter(f) 두가지로만 나뉩니다.");
            }

            // error 체크
            if(resGetAccountDTO.getError() != null){
                bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTO, KASRequestType.GET_ACCOUNT_BY_ADDRESS, reqCreateBlockChainInfoByNumberDTO.getServiceCode()));
                throw new DkargoException(ErrorCodeEnum.BAD_REQUEST, "KAS[getAccount] 통신 에러가 발생하였습니다.");
            }

            // EOA 또는 SCA 인지 판별 후 저장할 객체 생성 후 저장 할 List에 추가
            if(resGetAccountDTO != null) {

                switch (resGetAccountDTO.getResult().getAccount().getType()){

                    case "EOA" :
                        // DB에 저장되어있는지 확인 후 없으면 그대로, 있으면 업데이트하여 EOA List에 추가
                        Eoa eoa = eoaRepository.findByResult_AddressAndServiceCode(addressByAll, reqCreateBlockChainInfoByNumberDTO.getServiceCode());

                        if(eoa == null) {
                            ReqEoaDTO reqEoaDTO = ReqEoaDTO.builder()
                                    .jsonrpc(resGetAccountDTO.getJsonrpc())
                                    .resultByGetAccount(resGetAccountDTO.getResult())
                                    .serviceCode(reqCreateBlockChainInfoByNumberDTO.getServiceCode())
                                    .build();
                            eoas.add(EoaByKASConverter.of(reqEoaDTO));
                            validTransaction = true;
                        } else {
                            eoa.update(resGetAccountDTO);
                            eoas.add(eoa);
                            validTransaction = true;
                        }
                        break;

                    case "SCA" :
                        // DB에 저장되어있는지 확인 후 없으면 그대로, 있으면 업데이트하여 SCA List에 추가
                        Sca sca = scaRepository.findByResult_AddressAndServiceCode(addressByAll, reqCreateBlockChainInfoByNumberDTO.getServiceCode());

                        if(sca == null) {
                            ReqScaDTO reqScaDTO = ReqScaDTO.builder()
                                    .jsonrpc(resGetAccountDTO.getJsonrpc())
                                    .resultByGetAccount(resGetAccountDTO.getResult())
                                    .serviceCode(reqCreateBlockChainInfoByNumberDTO.getServiceCode())
                                    .build();
                            scas.add(ScaByKASConverter.of(reqScaDTO));
                            validTransaction = true;
                        } else {
                            sca.update(resGetAccountDTO);
                            scas.add(sca);
                            validTransaction = true;
                        }
                        break;
                }
            }
        }

        // 블록 / 트랜잭션 / 계정 정보 저장 - 한개라도 트랜잭션이 생성되면 해당 블록/트랜잭션/계정 정보 저장
        if(validTransaction) {

            // 블록 정보 저장 (블록 + consensus 정보)
            ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
                    .jsonrpc(resGetBlockDTO.getJsonrpc())
                    .resultByGetBlock(resGetBlockDTO.getResult())
                    .resultByGetBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO.getResult())
                    .totalTxFee(totalTxFee) // 해당 값은 Filter에 해당 하는 값이 아니여도, 모두 저장
                    .serviceCode(reqCreateBlockChainInfoByNumberDTO.getServiceCode())
                    .transactionHashs(transactionHashList) // 해당 값은 Filter에 해당 하는 값이 아니여도, 모두 저장
                    .build();
            io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));

            transactionRepository.saveAll(transactions);

            // 계정(EOA/SCA) 정보 저장
            if(eoas.size() > 0) {
                eoaRepository.saveAll(eoas);
            }
            if(scas.size() > 0) {
                scaRepository.saveAll(scas);
            }

            return new ResCreateBlockChainInfoDTO(CommonConverter.hexToLong(block.getResult().getNumber()), block.getResult().getHash(), transactionHashList);
        }

        return new ResCreateBlockChainInfoDTO(null, null, transactionHashList);
    }

//    @Override
//    public ResCreateBlockChainInfoDTO createBlockChainInfoByHash(ReqCreateBlockChainInfoByHashDTO reqCreateBlockByHashDTO) {
//
//        // 블록 + 트랜잭션 해시 정보 조회
//        ResGetBlockDTO resGetBlockDTO = getBlockByHash(reqCreateBlockByHashDTO.getBlockHash());
//
//        // 에러 체크
//        if(resGetBlockDTO.getError() != null) {
//
//            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlock(resGetBlockDTO, KASRequestType.GET_BLOCK_BY_HASH, reqCreateBlockByHashDTO.getServiceCode()));
//            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
//        }
//
//        // 블록 consensus 정보 조회 (proposer, committee)
//        ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO = getBlockWithConsensusInfoByHash(reqCreateBlockByHashDTO.getBlockHash());
//
//        // 에러 체크
//        if(resGetBlockWithConsensusInfoDTO.getError() != null) {
//
//            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_HASH, reqCreateBlockByHashDTO.getServiceCode()));
//            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
//        }
//
//        // 해당 블록의 트랜잭션 리스트 조회
//        ResGetBlockReceiptDTO resGetBlockReceiptDTO = getBlockReceiptByHash(resGetBlockDTO.getResult().getHash());
//
//        // 에러 체크
//        if(resGetBlockReceiptDTO.getError() != null) {
//
//            bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getBlockReceipt(resGetBlockReceiptDTO, KASRequestType.GET_BLOCK_RECEIPTS, reqCreateBlockByHashDTO.getServiceCode()));
//            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
//        }
//
//        // totalTxFee(block 정보) 생성
//        // transaction 해시 리스트(response 정보) 생성
//        // account(eoa 리스트/sca 리스트) 생성
//        Double totalTxFee = 0d;
//        List<String> transactionHashList = new ArrayList<>();
//        List<Eoa> eoas = new ArrayList<>();
//        List<Sca> scas = new ArrayList<>();
//        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {
//
//            totalTxFee += Double.parseDouble(result.getTxFee());
//            transactionHashList.add(result.getTransactionHash());
//
//            // from : 무조건 EOA 만 존재 ------------------------------------------------
//
//            // from 값이 DB 에 저장되어있는지 확인 후 없으면 저장 List에 추가, 있으면 바로 수정
//            Eoa eoaByFrom = eoaRepository.findByResult_address(result.getFrom());
//
//            // 해당 값의 최신 정보 조회
//            ResGetAccountDTO resGetAccountDTOByFrom = accountByKASService.getAccountByAddress(result.getFrom());
//            // 에러 체크
//            if(resGetAccountDTOByFrom.getError() != null) {
//
//                bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByFrom, KASRequestType.GET_ACCOUNT_BY_ADDRESS, reqCreateBlockByHashDTO.getServiceCode()));
//                throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
//            }
//
//            if(eoaByFrom == null) {
//                // 저장 할 EOA 정보 생성 후 EOA 리스트에 추가
//                ReqEoaDTO reqEoaDTOByFrom = ReqEoaDTO.builder()
//                        .jsonrpc(resGetAccountDTOByFrom.getJsonrpc())
//                        .resultByGetAccount(resGetAccountDTOByFrom.getResult())
//                        .build();
//                eoas.add(EoaByKASConverter.of(reqEoaDTOByFrom));
//            } else {
//                // 수정 할 EOA 정보 수정 후 EOA 리스트에 추가
//                eoaByFrom.update(resGetAccountDTOByFrom, resGetAccountDTOByFrom.getResult().getAccount().getBalanceByFormat(), resGetAccountDTOByFrom.getResult().getAccount().getTotalTransaction());
//                eoas.add(eoaByFrom);
//            }
//            // ------------------------------------------------------------------------
//
//            // to : EOA or SCA or NUll ------------------------------------------------
//            if(result.getTo() != null) {
//
//                ResGetAccountDTO resGetAccountDTOByTo = accountByKASService.getAccountByAddress(result.getTo());
//                // 에러 체크
//                if(resGetAccountDTOByFrom.getError() != null) {
//
//                    bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByTo, KASRequestType.GET_ACCOUNT_BY_ADDRESS, reqCreateBlockByHashDTO.getServiceCode()));
//                    throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
//                }
//
//                switch (resGetAccountDTOByTo.getResult().getAccount().getType().toString()) {
//
//                    case "EOA" :
//                        // to 값이 DB 에 저장되어있는지 확인 후 없으면 저장 List에 추가, 있으면 바로 수정
//                        Eoa eoaByTo = eoaRepository.findByResult_address(result.getTo());
//
//                        if(eoaByTo == null) {
//                            // 저장 할 EOA 정보 생성 후 EOA 리스트에 추가
//                            ReqEoaDTO reqEoaDTOByTo = ReqEoaDTO.builder()
//                                    .jsonrpc(resGetAccountDTOByTo.getJsonrpc())
//                                    .resultByGetAccount(resGetAccountDTOByTo.getResult())
//                                    .build();
//                            eoas.add(EoaByKASConverter.of(reqEoaDTOByTo));
//                        } else {
//                            // 수정 할 EOA 정보 수정 후 EOA 리스트에 추가
//                            eoaByTo.update(resGetAccountDTOByTo, resGetAccountDTOByTo.getResult().getAccount().getBalanceByFormat(), resGetAccountDTOByTo.getResult().getAccount().getTotalTransaction());
//                            eoas.add(eoaByTo);
//                        }
//                        break;
//
//                    case "SCA" :
//                        // to 값이 DB 에 저장되어있는지 확인 후 없으면 저장 List에 추가, 있으면 바로 수정
//                        Sca scaByTo = scaRepository.findByResult_address(result.getTo());
//
//                        if(scaByTo == null) {
//                            // 저장 할 SCA 정보 생성 후 SCA 리스트에 추가
//                            ReqScaDTO reqScaDTOByTo = ReqScaDTO.builder()
//                                    .jsonrpc(resGetAccountDTOByTo.getJsonrpc())
//                                    .resultByGetAccount(resGetAccountDTOByTo.getResult())
//                                    .build();
//                            scas.add(ScaByKASConverter.of(reqScaDTOByTo));
//                        } else {
//                            // 수정 할 SCA 정보 수정 후 SCA 리스트에 추가
//                            scaByTo.update(resGetAccountDTOByTo, resGetAccountDTOByTo.getResult().getAccount().getBalanceByFormat(), resGetAccountDTOByTo.getResult().getAccount().getTotalTransaction());
//                            scas.add(scaByTo);
//                        }
//                        break;
//
//                    default :
//                        break;
//                }
//            } else {
//
//                if(result.getContractAddress() != null) {
//
//                    ResGetAccountDTO resGetAccountDTOByContractAddress = accountByKASService.getAccountByAddress(result.getContractAddress());
//
//                    // 에러 체크
//                    if(resGetAccountDTOByFrom.getError() != null) {
//
//                        bcCommErrorRepository.save(bcCommErrorHandlingByKASService.getAccount(resGetAccountDTOByContractAddress, KASRequestType.GET_BLOCK_WITH_CONSENSUS_INFO_BY_NUMBER, reqCreateBlockByHashDTO.getServiceCode()));
//                        throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
//                    }
//
//                    Sca scaByContractAddress = scaRepository.findByResult_address(result.getContractAddress());
//
//                    if (scaByContractAddress == null) {
//                        // 저장 할 SCA 정보 생성 후 SCA 리스트에 추가
//                        ReqScaDTO reqScaDTOByContractAddress = ReqScaDTO.builder()
//                                .jsonrpc(resGetAccountDTOByContractAddress.getJsonrpc())
//                                .resultByGetAccount(resGetAccountDTOByContractAddress.getResult())
//                                .build();
//                        scas.add(ScaByKASConverter.of(reqScaDTOByContractAddress));
//                    } else {
//                        scaByContractAddress.update(resGetAccountDTOByContractAddress, resGetAccountDTOByContractAddress.getResult().getAccount().getBalanceByFormat(), resGetAccountDTOByContractAddress.getResult().getAccount().getTotalTransaction());
//                    }
//                } else {
//                    log.info("Chain Data Anchoring");
//                }
//            }
//            // ---------------------------------------------------------------------
//        }
//
//        // 블록 정보 저장 (블록 + consensus 정보)
//        ReqBlockDTO reqBlockDTO = ReqBlockDTO.builder()
//                .jsonrpc(resGetBlockDTO.getJsonrpc())
//                .resultByGetBlock(resGetBlockDTO.getResult())
//                .resultByGetBlockWithConsensusInfo(resGetBlockWithConsensusInfoDTO.getResult())
//                .totalTxFee(totalTxFee)
//                .build();
//        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.save(BlockByKASConverter.of(reqBlockDTO));
//
//
//        // 트랜잭션 정보 저장
//        List<io.dkargo.bcexplorer.domain.entity.Transaction> transactions = new ArrayList<>();
//        for(ResGetBlockReceiptDTO.Result result : resGetBlockReceiptDTO.getResults()) {
//
//            ReqTransactionDTO reqTransactionDTO = ReqTransactionDTO.builder()
//                    .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
//                    .result(result)
//                    .build();
//            transactions.add(TransactionByKASConverter.of(reqTransactionDTO));
//        }
//
//        transactionRepository.saveAll(transactions);
//
//        // 계정(EOA/SCA) 정보 저장
//        if(eoas.size() > 0) {
//            eoaRepository.saveAll(eoas);
//        }
//        if(scas.size() > 0) {
//            scaRepository.saveAll(scas);
//        }
//
//        return new ResCreateBlockChainInfoDTO(CommonConverter.hexToLong(block.getResult().getNumber()), block.getResult().getHash(), transactionHashList);
//    }

    @Override
    public ResCreateBlockChainInfoDTO createBlockChainInfoByScheduler(ReqCreateBlockChainInfoBySchedulerDTO reqCreateBlockChainInfoBySchedulerDTO) {

        // private node 에는 kas를 사용할 수 없음 (애초부터 이런 요청은 에러 처리!)
        if(reqCreateBlockChainInfoBySchedulerDTO.getChain().equals("private")){
            throw new DkargoException(ErrorCodeEnum.BAD_REQUEST);
        }

        // DB 저장된 블록 정보 중 블록 번호가 가장 큰(최신) 블록 정보 조회 - 요청한 serviceCode에 해당하는 Data 중..
        io.dkargo.bcexplorer.domain.entity.Block block = blockRepository.findTop1ByServiceCodeOrderByResult_NumberDesc(reqCreateBlockChainInfoBySchedulerDTO.getServiceCode());

        Long nextBlockNumber;
        ReqCreateBlockChainInfoByNumberDTO reqCreateBlockChainInfoByNumberDTO;
        ResCreateBlockChainInfoDTO resCreateBlockChainInfoDTO = null;

        // DB에 이전 블록 정보가 있을 시 - 존재하는 정보의 다음 번호부터 생성
        if(block != null) {
            log.info("latest blockNumber : {}", CommonConverter.hexToLong(block.getResult().getNumber()));

            nextBlockNumber = CommonConverter.hexToLong(block.getResult().getNumber()) + 1;
            log.info("nextBlockNumber(createBlockNumber) : {}", nextBlockNumber);

            reqCreateBlockChainInfoByNumberDTO = ReqCreateBlockChainInfoByNumberDTO.builder()
                    .chain(reqCreateBlockChainInfoBySchedulerDTO.getChain())
                    .communication(reqCreateBlockChainInfoBySchedulerDTO.getCommunication())
                    .serviceCode(reqCreateBlockChainInfoBySchedulerDTO.getServiceCode())
                    .blockNumber(nextBlockNumber)
                    .build();

            resCreateBlockChainInfoDTO = createBlockChainInfoByNumber(reqCreateBlockChainInfoByNumberDTO);
        }
        // DB에 이번 블록 정보가 없을 시
        else {

            // 지정한 특정 블록부터 수행 할 시 - 지정한 번호부터 생성
            if(selectBlock) {

                nextBlockNumber = selectBlockNumber;
                log.info("nextBlockNumber(createBlockNumber) : {}", nextBlockNumber);

                reqCreateBlockChainInfoByNumberDTO = ReqCreateBlockChainInfoByNumberDTO.builder()
                        .chain(reqCreateBlockChainInfoBySchedulerDTO.getChain())
                        .communication(reqCreateBlockChainInfoBySchedulerDTO.getCommunication())
                        .serviceCode(reqCreateBlockChainInfoBySchedulerDTO.getServiceCode())
                        .blockNumber(nextBlockNumber)
                        .build();

                resCreateBlockChainInfoDTO = createBlockChainInfoByNumber(reqCreateBlockChainInfoByNumberDTO);
            }
            // 지정한 특정 블록부터 수행하지 않을 시 - 현재 최신 블록부터 조회
            else {

                ResGetLatestBlockNumberDTO resGetLatestBlockNumberDTO = getLatestBlockNumber();
                nextBlockNumber = resGetLatestBlockNumberDTO.getBlockNumber();
                log.info("nextBlockNumber :{}", nextBlockNumber);

                reqCreateBlockChainInfoByNumberDTO = ReqCreateBlockChainInfoByNumberDTO.builder()
                        .chain(reqCreateBlockChainInfoBySchedulerDTO.getChain())
                        .communication(reqCreateBlockChainInfoBySchedulerDTO.getCommunication())
                        .serviceCode(reqCreateBlockChainInfoBySchedulerDTO.getServiceCode())
                        .blockNumber(nextBlockNumber)
                        .build();

                resCreateBlockChainInfoDTO = createBlockChainInfoByNumber(reqCreateBlockChainInfoByNumberDTO);
            }
        }

        return resCreateBlockChainInfoDTO;
    }
}
