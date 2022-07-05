package io.dkargo.bcexplorer.collector.service.impl;

import com.klaytn.caver.methods.response.KlayLogs;
import com.klaytn.caver.methods.response.Transaction;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.wallet.keyring.SignatureData;
import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import io.dkargo.bcexplorer.collector.service.converter.CommonConverter;
import io.dkargo.bcexplorer.dto.collector.kas.transaction.response.ResGetTransactionReceiptByHashDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionByKASServiceImpl implements TransactionByKASService {

    private final CaverExtKAS caverExtKAS;

    // transaction receipt 정보 조회 포멧
    public static ResGetTransactionReceiptByHashDTO getTransactionReceipt(TransactionReceipt transactionReceipt) {

        ResGetTransactionReceiptByHashDTO.Result result = null;
        if(transactionReceipt.getResult() != null) {

            // result -> feePayerSignatures 생성
            List<ResGetTransactionReceiptByHashDTO.Result.FeePayerSignature> feePayerSignatures = new ArrayList<>();
            if(transactionReceipt.getResult().getFeePayerSignatures() != null) {
                for(SignatureData signatureData : transactionReceipt.getResult().getFeePayerSignatures()) {

                    feePayerSignatures.add(
                            ResGetTransactionReceiptByHashDTO.Result.FeePayerSignature.builder()
                                    .v(signatureData.getV())
                                    .r(signatureData.getR())
                                    .s(signatureData.getS())
                                    .build()
                    );
                }
            }

            // result -> logs 생성
            List<ResGetTransactionReceiptByHashDTO.Result.Log> logs = new ArrayList<>();
            if(transactionReceipt.getResult().getLogs() != null) {
                for(KlayLogs.Log log : transactionReceipt.getResult().getLogs()) {


                    logs.add(
                            ResGetTransactionReceiptByHashDTO.Result.Log.builder()
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
            List<ResGetTransactionReceiptByHashDTO.Result.Signature> signatures = new ArrayList<>();
            for(SignatureData signatureData : transactionReceipt.getResult().getSignatures()) {

                signatures.add(
                        ResGetTransactionReceiptByHashDTO.Result.Signature.builder()
                                .v(signatureData.getV())
                                .r(signatureData.getR())
                                .s(signatureData.getS())
                                .build()
                );
            }

            // gasPrice / txFee / amount 값 생성
            Float gasPriceToFloat = null;
            String gasPriceToString = null;
            if(transactionReceipt.getResult().getGasPrice() != null) {
                gasPriceToFloat = CommonConverter.hexToKlayUnit(transactionReceipt.getResult().getGasPrice()); // gasPrice(hex) 값을 Klay 단위에 맞게 변경
                gasPriceToString = CommonConverter.floatToFormatString(gasPriceToFloat);
                log.info("gasPriceToString : {}", CommonConverter.floatToFormatString(gasPriceToFloat));
            }

            Float txFee = null;
            String txFeeToString = null;
            if(gasPriceToFloat != null && transactionReceipt.getResult().getGasUsed() != null) {
                txFee = gasPriceToFloat * CommonConverter.hexToLong(transactionReceipt.getResult().getGasUsed()); // gasPrice * gasUsed
                txFeeToString = CommonConverter.floatToFormatString(txFee);
                log.info("txFeeToString : {}", CommonConverter.floatToFormatString(txFee));
            }

            BigDecimal amount = null;
            String amountToString = null;
            if(transactionReceipt.getResult().getValue() != null) {
                amount = CommonConverter.hexToBigDecimal(transactionReceipt.getResult().getValue());
                amountToString = CommonConverter.bigDecimalToFormatString(amount);
                log.info("amountToString : {}", CommonConverter.bigDecimalToFormatString(amount));
            }

            // methodSig 값 생성 (input 값이 "0x" 일 수도 있음)
            String methodSig = null;
            if(transactionReceipt.getResult().getInput() != null) {
                if(transactionReceipt.getResult().getInput().length() >= 10){
                    methodSig = transactionReceipt.getResult().getInput().substring(0,10); // input 의 첫번째 자릿수 부터 10번째 자릿수 까지 문자열
                    log.info("methodSig : {}", transactionReceipt.getResult().getInput().substring(0,10));
                } else {
                    methodSig = transactionReceipt.getResult().getInput();
                    log.info("methodSig : {}", transactionReceipt.getResult().getInput());
                }
            }

            // result 생성
            result = ResGetTransactionReceiptByHashDTO.Result.builder()
                    .blockHash(transactionReceipt.getResult().getBlockHash())
                    .blockNumber(transactionReceipt.getResult().getBlockNumber())
                    .codeFormat(transactionReceipt.getResult().getCodeFormat())
                    .contractAddress(transactionReceipt.getResult().getContractAddress())
                    .feePayer(transactionReceipt.getResult().getFeePayer())
                    .feePayerSignatures(feePayerSignatures)
                    .feeRatio(transactionReceipt.getResult().getFeeRatio())
                    .from(transactionReceipt.getResult().getFrom())
                    .gas(transactionReceipt.getResult().getGas())
                    .gasPrice(transactionReceipt.getResult().getGasPrice())
                    .gasPriceByFormat(gasPriceToString)
                    .gasUsed(transactionReceipt.getResult().getGasUsed())
                    .txFee(txFeeToString)
                    .key(transactionReceipt.getResult().getKey())
                    .input(transactionReceipt.getResult().getInput())
                    .logs(logs)
                    .logsBloom(transactionReceipt.getResult().getLogsBloom())
                    .nonce(transactionReceipt.getResult().getNonce())
                    .senderTxHash(transactionReceipt.getResult().getSenderTxHash())
                    .signatures(signatures)
                    .status(transactionReceipt.getResult().getStatus())
                    .to(transactionReceipt.getResult().getTo())
                    .transactionIndex(transactionReceipt.getResult().getTransactionIndex())
                    .txError(transactionReceipt.getResult().getTxError())
                    .type(transactionReceipt.getResult().getType())
                    .typeInt(transactionReceipt.getResult().getTypeInt())
                    .value(transactionReceipt.getResult().getValue())
                    .amount(amountToString)
                    .methodSig(methodSig)
                    .build();

        }

        // error 생성
        ResGetTransactionReceiptByHashDTO.Error error = null;
        if (transactionReceipt.getError() != null) {
            error = ResGetTransactionReceiptByHashDTO.Error.builder()
                    .code(transactionReceipt.getError().getCode())
                    .message(transactionReceipt.getError().getMessage())
                    .data(transactionReceipt.getError().getData())
                    .build();
        }

        ResGetTransactionReceiptByHashDTO resGetTransactionReceiptByHashDTO = ResGetTransactionReceiptByHashDTO.builder()
                .id(transactionReceipt.getId())
                .jsonrpc(transactionReceipt.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(transactionReceipt.getRawResponse())
                .build();

        return resGetTransactionReceiptByHashDTO;
    }

    @Override
    public void getTransactionByHash(String transactionHash) {

        try {
            Transaction transaction = caverExtKAS.rpc.klay.getTransactionByHash(transactionHash).send();
            log.info("transaction : {}", CommonConverter.objectToString(transaction));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResGetTransactionReceiptByHashDTO getTransactionReceiptByHash(String transactionHash) {

        ResGetTransactionReceiptByHashDTO resGetTransactionReceiptByHashDTO = null;

        try {
            TransactionReceipt transactionReceipt = caverExtKAS.rpc.klay.getTransactionReceipt(transactionHash).send();
            log.info("transactionReceipt : {}", CommonConverter.objectToString(transactionReceipt));

            resGetTransactionReceiptByHashDTO = getTransactionReceipt(transactionReceipt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetTransactionReceiptByHashDTO;
    }

}
