package io.dkargo.bcexplorer.scheduler.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bcexplorer.collector.client.BcExplorerCollectorClient;
import io.dkargo.bcexplorer.collector.client.BcExplorerCollectorConfigData;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResCreateBlockChainInfoDTO;
import io.dkargo.bcexplorer.scheduler.service.CollectorService;
import io.dkargo.dkargohttpclient.DkargoHttpClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class CollectorServiceImpl implements CollectorService {

    @Value("${bcexplorer.collector.url}")
    private String url;

    private final ObjectMapper objectMapper;
    private BcExplorerCollectorClient bcExplorerCollectorClient;

    public CollectorServiceImpl(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    @PostConstruct
    private void postConstruct() {

        log.info("asdfsafsad : {}", url);
        BcExplorerCollectorConfigData configData = new BcExplorerCollectorConfigData(url);
        this.bcExplorerCollectorClient = new BcExplorerCollectorClient(configData, objectMapper);
    }

    @Override
    public ResCreateBlockChainInfoDTO createBlockWithTransaction() {

        ResCreateBlockChainInfoDTO resCreateBlockDTO = null;

        try {
            log.info("createBlockWithTransaction start!!");
            resCreateBlockDTO = bcExplorerCollectorClient.createBlockWithTransaction();
            log.info("createBlockWithTransaction end!!");
        } catch (IOException e){
            log.error("IOException : ", e);
        } catch (DkargoHttpClientResponseException e) {
            log.error("{}", e, e);
        }

        return resCreateBlockDTO;
    }
}
