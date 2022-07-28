package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.core.type.KASRequestType;
import io.dkargo.bcexplorer.domain.entity.BcCommError;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockReceiptDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockWithConsensusInfoDTO;

public interface BcCommErrorHandlingByKASService {

    // block
    BcCommError getBlock(ResGetBlockDTO resGetBlockDTO, KASRequestType kasRequestType, String serviceCode);
    BcCommError getBlockWithConsensusInfo(ResGetBlockWithConsensusInfoDTO resGetBlockWithConsensusInfoDTO, KASRequestType kasRequestType, String serviceCode);
    BcCommError getBlockReceipt(ResGetBlockReceiptDTO resGetBlockReceiptDTO, KASRequestType kasRequestType, String serviceCode);

    // account
    BcCommError getAccount(ResGetAccountDTO resGetAccountDTO, KASRequestType kasRequestType, String serviceCode);


}
