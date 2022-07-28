package io.dkargo.bcexplorer.collector.service.impl;

import io.dkargo.bcexplorer.collector.service.BcCommErrorHandlingByKASService;
import io.dkargo.bcexplorer.collector.service.converter.BcCommErrorByKASConverter;
import io.dkargo.bcexplorer.core.type.KASRequestType;
import io.dkargo.bcexplorer.domain.entity.BcCommError;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockReceiptDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockWithConsensusInfoDTO;
import io.dkargo.bcexplorer.dto.domain.kas.bccommerror.request.ReqBcCommErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BcCommErrorHandlingByKASServiceImpl implements BcCommErrorHandlingByKASService {

    // block
    @Override
    public BcCommError getBlock(ResGetBlockDTO resGetBlockDTO, KASRequestType kasRequestType, String serviceCode) {

        ReqBcCommErrorDTO.Error error = ReqBcCommErrorDTO.Error.builder()
                .code(resGetBlockDTO.getError().getCode())
                .message(resGetBlockDTO.getError().getMessage())
                .data(resGetBlockDTO.getError().getData())
                .build();

        ReqBcCommErrorDTO reqBcCommErrorDTO = ReqBcCommErrorDTO.builder()
                .id(resGetBlockDTO.getId())
                .jsonrpc(resGetBlockDTO.getJsonrpc())
                .error(error)
                .kasRequestType(kasRequestType)
                .rawResponse(resGetBlockDTO.getRawResponse())
                .serviceCode(serviceCode)
                .build();

        return BcCommErrorByKASConverter.of(reqBcCommErrorDTO);
    }

    @Override
    public BcCommError getBlockWithConsensusInfo(ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO, KASRequestType kasRequestType, String serviceCode) {

        ReqBcCommErrorDTO.Error error = ReqBcCommErrorDTO.Error.builder()
                .code(resGetBlockWithConsensusInfoDTO.getError().getCode())
                .message(resGetBlockWithConsensusInfoDTO.getError().getMessage())
                .data(resGetBlockWithConsensusInfoDTO.getError().getData())
                .build();

        ReqBcCommErrorDTO reqBcCommErrorDTO = ReqBcCommErrorDTO.builder()
                .id(resGetBlockWithConsensusInfoDTO.getId())
                .jsonrpc(resGetBlockWithConsensusInfoDTO.getJsonrpc())
                .error(error)
                .kasRequestType(kasRequestType)
                .rawResponse(resGetBlockWithConsensusInfoDTO.getRawResponse())
                .serviceCode(serviceCode)
                .build();

        return BcCommErrorByKASConverter.of(reqBcCommErrorDTO);
    }

    @Override
    public BcCommError getBlockReceipt(ResGetBlockReceiptDTO resGetBlockReceiptDTO, KASRequestType kasRequestType, String serviceCode) {
        ReqBcCommErrorDTO.Error error = ReqBcCommErrorDTO.Error.builder()
                .code(resGetBlockReceiptDTO.getError().getCode())
                .message(resGetBlockReceiptDTO.getError().getMessage())
                .data(resGetBlockReceiptDTO.getError().getData())
                .build();

        ReqBcCommErrorDTO reqBcCommErrorDTO = ReqBcCommErrorDTO.builder()
                .id(resGetBlockReceiptDTO.getId())
                .jsonrpc(resGetBlockReceiptDTO.getJsonrpc())
                .error(error)
                .kasRequestType(kasRequestType)
                .rawResponse(resGetBlockReceiptDTO.getRawResponse())
                .serviceCode(serviceCode)
                .build();

        return BcCommErrorByKASConverter.of(reqBcCommErrorDTO);
    }

    // account
    @Override
    public BcCommError getAccount(ResGetAccountDTO resGetAccountDTO, KASRequestType kasRequestType, String serviceCode) {

        ReqBcCommErrorDTO.Error error = ReqBcCommErrorDTO.Error.builder()
                .code(resGetAccountDTO.getError().getCode())
                .message(resGetAccountDTO.getError().getMessage())
                .data(resGetAccountDTO.getError().getData())
                .build();

        ReqBcCommErrorDTO reqBcCommErrorDTO = ReqBcCommErrorDTO.builder()
                .id(resGetAccountDTO.getId())
                .jsonrpc(resGetAccountDTO.getJsonrpc())
                .error(error)
                .kasRequestType(kasRequestType)
                .rawResponse(resGetAccountDTO.getRawResponse())
                .serviceCode(serviceCode)
                .build();

        return BcCommErrorByKASConverter.of(reqBcCommErrorDTO);
    }
}
