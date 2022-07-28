package io.dkargo.bcexplorer.scheduler.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bcexplorer.collector.client.BcExplorerCollectorClient;
import io.dkargo.bcexplorer.collector.client.BcExplorerCollectorConfigData;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoBySchedulerDTO;
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

    @Value("${service.select.chain}")
    private String chain;

    @Value("${service.select.communication}")
    private String communication;

    @Value("${service.select.service-code")
    private String serviceCode;

    private final ObjectMapper objectMapper;
    private BcExplorerCollectorClient bcExplorerCollectorClient;

    public CollectorServiceImpl(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    @PostConstruct
    private void postConstruct() {

        BcExplorerCollectorConfigData configData = new BcExplorerCollectorConfigData(url);
        this.bcExplorerCollectorClient = new BcExplorerCollectorClient(configData, objectMapper);
    }

    @Override
    public ResCreateBlockChainInfoDTO createBlockChainInfo() {


        ReqCreateBlockChainInfoBySchedulerDTO reqCreateBlockChainInfoBySchedulerDTO = ReqCreateBlockChainInfoBySchedulerDTO.builder()
                .chain(chain)
                .communication(communication)
                .serviceCode(serviceCode)
                .build();
        ResCreateBlockChainInfoDTO resCreateBlockDTO = null;

        // BC 통신 방법 확인 (caver or kas)
        switch (reqCreateBlockChainInfoBySchedulerDTO.getCommunication()) {

            case "caver" :
                log.info("caver~!!");
                break;

            case "kas" :
                log.info("kas~!!");

                try {
                    log.info("createBlockWithTransaction start!!");
                    resCreateBlockDTO = bcExplorerCollectorClient.createBlockChainInfoByKas(reqCreateBlockChainInfoBySchedulerDTO);
                    log.info("createBlockWithTransaction end!!");
                } catch (IOException e){
                    log.error("IOException : ", e);
                } catch (DkargoHttpClientResponseException e) {
                    log.error("{}", e, e);
                }
                break;

            default:
                break;
        }

        return resCreateBlockDTO;
    }
}
