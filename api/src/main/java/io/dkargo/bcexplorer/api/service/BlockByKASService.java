package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListInDashboardDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetLatestBlockNumberDTO;

public interface BlockByKASService {

    ResGetBlockDTO getBlockByNumber(Long blockNumber);

    ResGetBlockListDTO getBlockList(Integer page, Integer size);

    ResGetLatestBlockNumberDTO getLatestBlockNumber();

    ResGetBlockListInDashboardDTO getBlockListInDashboard(Integer page, Integer size);
}
