package io.dkargo.bcexplorer.collector.service.impl;

import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.collector.service.DashboardByKASService;
import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardByKASServiceImpl implements DashboardByKASService {

    BlockByKASService blockByKASService;
    TransactionByKASService transactionByKASService;

    @Override
    public void getRecentDashboardInfo() {

        blockByKASService.getLatestBlockNumber();
    }
}
