package io.dkargo.bcexplorer.scheduler.worker.collector;

import com.munzi.munzischeduler.SchedulerWorkerBase;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResCreateBlockChainInfoDTO;
import io.dkargo.bcexplorer.scheduler.service.CollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateBlockChainInfoWorker extends SchedulerWorkerBase {

    private final CollectorService collectorService;

    @Override
    public void processJob(Map data) {

        ResCreateBlockChainInfoDTO resCreateBlockDTO = collectorService.createBlockChainInfo();
    }
}
