package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListDTO;

public interface BlockByKASService {

    ResGetBlockDTO getBlockByNumber(Long blockNumber);

    ResGetBlockListDTO getBlockList();
}
