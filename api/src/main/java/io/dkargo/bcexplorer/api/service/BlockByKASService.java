package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;

public interface BlockByKASService {

    ResGetBlockDTO getBlockByNumber(Long blockNumber);
}
