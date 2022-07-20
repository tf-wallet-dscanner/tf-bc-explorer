package io.dkargo.bcexplorer.scheduler.service;

import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResCreateBlockChainInfoDTO;

public interface CollectorService {

    ResCreateBlockChainInfoDTO createBlockWithTransaction();
}
